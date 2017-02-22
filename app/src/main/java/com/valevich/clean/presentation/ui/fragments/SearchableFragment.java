package com.valevich.clean.presentation.ui.fragments;


import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.SearchablePresenter;

import java.util.concurrent.TimeUnit;

import icepick.State;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

@RequiresPresenter(SearchablePresenter.class)
public class SearchableFragment extends StoriesFragment<SearchablePresenter> {

    private Subscription textChangeSubscription;

    @State
    String query;

    @State
    boolean wasExpanded;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        if (textChangeSubscription != null && !textChangeSubscription.isUnsubscribed())
            textChangeSubscription.unsubscribe();
        super.onDestroy();
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
    void getStories() {
        getPresenter().findStories("",Story.DEFAULT_COUNT, Story.DEFAULT_OFFSET);
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
                            // FIXME: 21.02.2017 shows progress at the bottom
                            this.query = query;
                            showProgress();
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
