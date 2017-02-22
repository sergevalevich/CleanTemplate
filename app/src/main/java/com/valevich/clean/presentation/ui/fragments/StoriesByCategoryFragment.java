package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.presenters.impl.StoriesByCategoryPresenter;

import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import timber.log.Timber;

@RequiresPresenter(StoriesByCategoryPresenter.class)
public class StoriesByCategoryFragment extends StoriesFragment<StoriesByCategoryPresenter> {

    private SwipeRefreshLayout swipe;

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
        View child = LayoutInflater.from(view.getContext()).inflate(R.layout.swipe, rootView, false);
        rootView.addView(child);
        rootView.removeView(storiesList);
        swipe = ButterKnife.findById(child,R.id.swipe);
        swipe.addView(storiesList);
        swipe.setOnRefreshListener(this::refreshStories);
        swipe.setRefreshing(true);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onStoriesRefreshed() {
        Timber.d("onStoriesRefreshed");
        swipe.setRefreshing(false);
    }

    @Override
    void getStories() {
        Bundle args = getArguments();
        getPresenter().getStories(
                args.getParcelable(CATEGORY_KEY),
                args.getInt(STORIES_COUNT_KEY),
                args.getInt(OFFSET_KEY));
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
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
