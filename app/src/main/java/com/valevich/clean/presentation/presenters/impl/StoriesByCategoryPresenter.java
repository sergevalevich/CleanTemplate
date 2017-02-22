package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment;

import icepick.State;


public class StoriesByCategoryPresenter extends StoriesPresenter<StoriesByCategoryFragment> {

    private static final int REFRESH_STORIES_TASK_ID = 6;
    private static final int LOAD_STORIES_TASK_ID = 5;

    @State
    Category category;

    @State
    int storiesCount;

    @State
    int offset;

    public StoriesByCategoryPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                LOAD_STORIES_TASK_ID,
                () -> getRepository().getByCategory(category, storiesCount,offset),
                StoriesByCategoryFragment::onStories,
                StoriesByCategoryFragment::onError);

        restartableLatestCache(
                REFRESH_STORIES_TASK_ID,
                () -> getRepository().getByCategory(category, storiesCount),
                (f,s) -> f.onStoriesRefreshed(),
                StoriesByCategoryFragment::onError);

    }

    public void refreshStories() {
        start(REFRESH_STORIES_TASK_ID);
    }

    public void getStories(Category category, int count, int offset) {
        this.category = category;
        this.storiesCount = count;
        this.offset = offset;
        start(LOAD_STORIES_TASK_ID);
    }
}
