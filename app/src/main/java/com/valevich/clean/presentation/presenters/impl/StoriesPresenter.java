package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.DbOpenHelper;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.domain.repository.IStoriesRepository;
import com.valevich.clean.domain.repository.impl.StoriesRepository;
import com.valevich.clean.network.RestService;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.fragments.StoriesFragment;

import icepick.State;

public abstract class StoriesPresenter<V extends StoriesFragment> extends BasePresenter<V> {

    private static final int UPDATE_STORY_TASK_ID = 1;

    @State
    Story storyToUpdate;

    private IStoriesRepository repository;

    StoriesPresenter(Context context) {
        repository = new StoriesRepository(new DatabaseHelper(new DbOpenHelper(context)),new RestService(),context);
    }

    //called in getPresenter first time
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableFirst(
                UPDATE_STORY_TASK_ID,
                () -> repository.update(storyToUpdate),
                StoriesFragment::onStoryUpdated,
                StoriesFragment::onError);
    }

    public void updateStory(Story story) {
        storyToUpdate = story;
        start(UPDATE_STORY_TASK_ID);
    }

    IStoriesRepository getRepository() {
        return repository;
    }
}
