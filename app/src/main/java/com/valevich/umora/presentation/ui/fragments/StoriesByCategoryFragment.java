package com.valevich.umora.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.umora.R;
import com.valevich.umora.UmoraApplication;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.presentation.presenters.impl.StoriesByCategoryPresenter;
import com.valevich.umora.presentation.ui.activities.StoriesByCategoryActivity;
import com.valevich.umora.presentation.ui.utils.AttributesHelper;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(StoriesByCategoryPresenter.class)
public class StoriesByCategoryFragment extends StoriesFragment<StoriesByCategoryPresenter> {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

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
        ((StoriesByCategoryActivity) getActivity()).getActivityComponent().inject(this);
        super.onCreate(bundle);
        if (bundle == null) {
            getCachedStories();
            refreshStories();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_updated,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe.setOnRefreshListener(this::refreshStories);
        swipe.setColorSchemeColors(AttributesHelper.getColorAttribute(getActivity(),R.attr.colorPrimary));
        toggleSwipe(true);
    }

    public void onStoriesRefreshed() {
        toggleSwipe(false);
    }

    @Override
    public void onError(Throwable t) {
        toggleSwipe(false);
        super.onError(t);
    }

    @Override
    void injectPresenter(StoriesByCategoryPresenter presenter) {
        ((UmoraApplication) getContext().getApplicationContext())
                .getAppComponent()
                .inject(presenter);
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

    private void toggleSwipe(boolean isVisible) {
        swipe.setRefreshing(isVisible);
    }
}
