package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.presenters.impl.StoriesByCategoryPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import timber.log.Timber;

@RequiresPresenter(StoriesByCategoryPresenter.class)
public class StoriesByCategoryFragment extends StoriesFragment<StoriesByCategoryPresenter> {

    public static final String CATEGORY_KEY = "CATEGORY";
    public static final String STORIES_COUNT_KEY = "COUNT";

    public static StoriesByCategoryFragment newInstance(Category category, int count) {
        StoriesByCategoryFragment f = new StoriesByCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY,category);
        args.putInt(STORIES_COUNT_KEY, count);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Timber.d("onCreate");
        if (bundle == null) {
            refreshStories();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe.setOnRefreshListener(this::refreshStories);
    }

    @Override
    void subscribeToUpdates() {
        Bundle args = getArguments();
        getPresenter().loadStories(
                args.getParcelable(CATEGORY_KEY),
                args.getInt(STORIES_COUNT_KEY));
    }

    @Override
    void showLoading() {
        swipe.setRefreshing(true);
    }

    @Override
    void hideLoading() {
        swipe.setRefreshing(false);
    }

    @Override
    PresenterFactory<StoriesByCategoryPresenter> createPresenterFactory() {
        return () -> new StoriesByCategoryPresenter(getActivity().getApplicationContext());
    }

    private void refreshStories() {
        getPresenter().refreshStories();
    }
}
