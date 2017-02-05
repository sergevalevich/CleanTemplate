package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.IStoryUpdateInteractor;
import com.valevich.clean.domain.model.Story;

import rx.Observable;

public class StoryUpdateInteractor implements IStoryUpdateInteractor {

    private IStoriesManager storiesManager;

    public StoryUpdateInteractor(IStoriesManager storiesManager) {
        this.storiesManager = storiesManager;
    }

    @Override
    public Observable<Boolean> updateStory(Story story) {
        return storiesManager.update(story);
    }
}
