package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.actionmode.StoriesActionModeHelper;
import com.valevich.clean.database.DbHelper;
import com.valevich.clean.database.DbOpenHelper;
import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.data.impl.StoriesManager;
import com.valevich.clean.domain.interactors.IStorySelectedInteractor;
import com.valevich.clean.domain.interactors.IStoryUpdateInteractor;
import com.valevich.clean.domain.interactors.impl.ActionModeInteractor;
import com.valevich.clean.domain.interactors.impl.StoryUpdateInteractor;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.network.RestService;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.fragments.StoriesFragment;

import icepick.State;

public abstract class StoriesPresenter<V extends StoriesFragment> extends BasePresenter<V> {

    private static final int UPDATE_STORY_TASK_ID = 1;

    @State
    Story storyToUpdate;

    private IStoriesManager storiesManager;
    private IStoryUpdateInteractor storyUpdateInteractor;
    private IStorySelectedInteractor storySelectedInteractor;

    StoriesPresenter(Context context,Context activityContext) {
        this.storiesManager = new StoriesManager(new DbHelper(new DbOpenHelper(context)), new RestService(), context);
        this.storyUpdateInteractor = new StoryUpdateInteractor(this.storiesManager);
        this.storySelectedInteractor = new ActionModeInteractor(new StoriesActionModeHelper(activityContext));
    }

    //called in getPresenter first time
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                UPDATE_STORY_TASK_ID,
                () -> storyUpdateInteractor.updateStory(storyToUpdate),
                StoriesFragment::onStoryUpdated,
                StoriesFragment::onError);
    }

    public void updateStory(Story story) {
        storyToUpdate = story;
        start(UPDATE_STORY_TASK_ID);
    }

    public void onStorySelected(Story story) {
        storySelectedInteractor.onStorySelected(story);
    }

    IStoriesManager getStoriesManager() {
        return storiesManager;
    }
}
