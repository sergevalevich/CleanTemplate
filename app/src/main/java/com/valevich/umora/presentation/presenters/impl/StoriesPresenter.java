package com.valevich.umora.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.umora.database.DatabaseHelper;
import com.valevich.umora.database.DbOpenHelper;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.model.Story;
import com.valevich.umora.domain.repository.IRepository;
import com.valevich.umora.domain.repository.impl.StoriesRepository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;
import com.valevich.umora.presentation.presenters.base.BasePresenter;
import com.valevich.umora.presentation.ui.fragments.StoriesFragment;

import icepick.State;

public abstract class StoriesPresenter<V extends StoriesFragment> extends BasePresenter<V> {

    private static final int UPDATE_STORY_TASK_ID = 0;
    private static final int LOAD_STORIES_TASK_ID = 1;

    @State
    Story storyToUpdate;

    @State
    SqlDelightSpecification<StoryEntity> specification;

    private IRepository<Story,SqlDelightSpecification<StoryEntity>> repository;
    private Context context;

    StoriesPresenter(Context context) {
        this.context = context;
        repository = new StoriesRepository(new DatabaseHelper(new DbOpenHelper(this.context)));
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

        restartableLatestCache(
                LOAD_STORIES_TASK_ID,
                () -> repository.get(specification),
                StoriesFragment::onStories,
                StoriesFragment::onError);
    }

    public void updateStory(Story story) {
        storyToUpdate = story;
        start(UPDATE_STORY_TASK_ID);
    }

    void loadStories(SqlDelightSpecification<StoryEntity> specification) {
        this.specification = specification;
        start(LOAD_STORIES_TASK_ID);
    }

    Context getContext() {
        return context;
    }

    IRepository<Story, SqlDelightSpecification<StoryEntity>> getRepository() {return repository;}
}
