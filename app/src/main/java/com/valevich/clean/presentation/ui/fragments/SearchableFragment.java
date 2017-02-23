package com.valevich.clean.presentation.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.SearchablePresenter;
import com.valevich.clean.presentation.ui.utils.PageBundle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import icepick.State;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

@RequiresPresenter(SearchablePresenter.class)
public class SearchableFragment extends StoriesFragment<SearchablePresenter> {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @State
    String query;

    @State
    boolean wasExpanded;

    private Subscription textChangeSubscription;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onDestroy() {
        if (textChangeSubscription != null && !textChangeSubscription.isUnsubscribed())
            textChangeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onStories(PageBundle<List<Story>> pageBundle) {
        super.onStories(pageBundle);
        swipe.setRefreshing(false);
        swipe.setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        setExpandListener(searchItem);
        if (wasExpanded) {
            restoreSearchState(searchView, searchItem);
        }
        subscribeToQueryTextChanges(searchView);
    }

    private void restoreSearchState(SearchView searchView, MenuItem searchItem) {
        MenuItemCompat.expandActionView(searchItem);
        searchView.setQuery(query, false);
    }

    @Override
    PresenterFactory<SearchablePresenter> createPresenterFactory() {
        return () -> new SearchablePresenter(getActivity().getApplicationContext());
    }

    private void subscribeToQueryTextChanges(SearchView searchView) {
        textChangeSubscription = RxSearchView.queryTextChanges(searchView)
                .debounce(700, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .filter(changes -> !changes.isEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                            Timber.d("query received/going to db");
                            swipe.setEnabled(true);
                            swipe.setRefreshing(true);
                            this.query = query;
                            getPresenter().findStories(query,
                                    Story.DEFAULT_COUNT,
                                    Story.DEFAULT_OFFSET);
                        },
                        throwable -> {
                            Timber.e("search error %s",throwable.getMessage());
                        });
    }

    private void setExpandListener(MenuItem searchItem) {
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                wasExpanded = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                wasExpanded = false;
                return true;
            }
        });
    }
}
