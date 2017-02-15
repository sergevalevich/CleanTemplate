package com.valevich.clean.presentation.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.SearchablePresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import icepick.State;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

@RequiresPresenter(SearchablePresenter.class)
public class SearchableFragment extends StoriesFragment<SearchablePresenter> {

    private Subscription textChangeSubscribtion;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideLoading();
    }

    @Override
    public void onDestroy() {
        if (textChangeSubscribtion != null && !textChangeSubscribtion.isUnsubscribed())
            textChangeSubscribtion.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
        if (query != null && wasExpanded) {
            MenuItemCompat.expandActionView(searchItem);
            searchView.setQuery(query,false);
        }
        textChangeSubscribtion = RxSearchView.queryTextChanges(searchView)
                .debounce(700, TimeUnit.MILLISECONDS)
                .doOnNext(changes -> this.query = changes.toString())
                .filter(changes -> !changes.toString().isEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                            showLoading();
                            getPresenter().findStories(query.toString(),
                                    Story.DEFAULT_COUNT,
                                    Story.DEFAULT_OFFSET);
                        },
                        throwable -> {
                            Timber.e("search error %s",throwable.getMessage());
                        });
    }

    @Override
    public void onStories(List<Story> stories) {
        super.onStories(stories);
        Timber.d("%d stories found", stories.size());
        hideLoading();
    }

    @Override
    void subscribeToUpdates() {

    }

    @Override
    void showLoading() {
        swipe.setEnabled(true);
        swipe.setRefreshing(true);
    }

    @Override
    void hideLoading() {
        swipe.setRefreshing(false);
        swipe.setEnabled(false);
    }

    @Override
    PresenterFactory<SearchablePresenter> createPresenterFactory() {
        return () -> new SearchablePresenter(getActivity().getApplicationContext());
    }
}
