package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.interactors.IStoriesByCategoryLoadingInteractor;
import com.valevich.clean.domain.interactors.IStoriesByCategoryRefreshingInteractor;
import com.valevich.clean.domain.interactors.impl.StoriesLoadingInteractor;
import com.valevich.clean.domain.interactors.impl.StoriesRefreshingInteractor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment;
import com.valevich.clean.presentation.ui.fragments.StoriesFragment;

import icepick.State;
import timber.log.Timber;


public class StoriesByCategoryPresenter extends StoriesPresenter<StoriesByCategoryFragment> {

    private static final int UPDATE_STORIES_TASK_ID = 6;
    private static final int LOAD_STORIES_TASK_ID = 5;

    @State
    Category category;

    @State
    int storiesCount;

    @State
    int offset;

    private IStoriesByCategoryLoadingInteractor loadingInteractor;
    private IStoriesByCategoryRefreshingInteractor refreshingInteractor;

    public StoriesByCategoryPresenter(Context context) {
        super(context);
        loadingInteractor = new StoriesLoadingInteractor(getStoriesManager());
        refreshingInteractor = new StoriesRefreshingInteractor(getStoriesManager());
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                LOAD_STORIES_TASK_ID,
                () -> loadingInteractor.loadStories(category, storiesCount,offset),
                StoriesByCategoryFragment::onStories,
                StoriesFragment::onError);

        restartableLatestCache(
                UPDATE_STORIES_TASK_ID,
                () -> refreshingInteractor.refreshStories(category, storiesCount),
                (f,s) -> f.onStoriesUpToDate(),
                StoriesFragment::onError);
    }

    public void refreshStories() {
        start(UPDATE_STORIES_TASK_ID);
    }

    public void loadStories(Category category, int count, int offset) {
        this.category = category;
        this.storiesCount = count;
        this.offset = offset;
        start(LOAD_STORIES_TASK_ID);
    }
}
