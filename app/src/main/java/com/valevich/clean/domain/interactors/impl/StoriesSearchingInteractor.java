package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.IBookMarksLoadingInteractor;
import com.valevich.clean.domain.interactors.IStoriesSearchingInteractor;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;


public class StoriesSearchingInteractor extends StoriesInteractor implements IStoriesSearchingInteractor {

    public StoriesSearchingInteractor(IStoriesManager storiesManager) {
        super(storiesManager);
    }

    @Override
    public Observable<List<Story>> loadStories(String filter,int count, int offset) {
        return storiesManager.findStories(filter,count,offset);
    }
}
