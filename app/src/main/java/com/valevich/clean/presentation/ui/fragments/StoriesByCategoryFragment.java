package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.presenters.impl.StoriesByCategoryPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(StoriesByCategoryPresenter.class)
public class StoriesByCategoryFragment extends StoriesFragment<StoriesByCategoryPresenter> {

    public static final String CATEGORY_KEY = "CATEGORY";
    public static final String STORIES_COUNT_KEY = "COUNT";
    public static final String OFFSET_KEY = "OFFSET";

    public static StoriesByCategoryFragment newInstance(Category category, int count,int offset) {
        StoriesByCategoryFragment f = new StoriesByCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY,category);
        args.putInt(STORIES_COUNT_KEY, count);
        args.putInt(OFFSET_KEY,offset);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            refreshStories();
        }
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
                args.getInt(STORIES_COUNT_KEY),
                args.getInt(OFFSET_KEY));
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
