package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.clean.R;
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
        if (bundle == null) {
            getCachedStories();
            refreshStories();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe.setOnRefreshListener(this::refreshStories);
        setSwipeActionEnabled(true);
        showLoading();
    }

    public void onStoriesRefreshed() {
        Timber.d("onStoriesRefreshed");
        hideLoading();
    }

    @Override
    PresenterFactory<StoriesByCategoryPresenter> createPresenterFactory() {
        return () -> new StoriesByCategoryPresenter(getActivity().getApplicationContext());
    }

    private void refreshStories() {
        Bundle args = getArguments();
        getPresenter().refreshStoriesByCategory(
                args.getParcelable(CATEGORY_KEY),
                args.getInt(STORIES_COUNT_KEY));
    }

    private void getCachedStories() {
        Bundle args = getArguments();
        getPresenter().getStoriesByCategory(args.getParcelable(CATEGORY_KEY));
    }
}
