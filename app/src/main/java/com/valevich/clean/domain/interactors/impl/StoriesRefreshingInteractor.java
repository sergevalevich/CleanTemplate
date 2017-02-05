package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.IStoriesByCategoryRefreshingInteractor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public class StoriesRefreshingInteractor extends StoriesInteractor implements IStoriesByCategoryRefreshingInteractor {


    public StoriesRefreshingInteractor(IStoriesManager storiesManager) {
        super(storiesManager);
    }

    @Override
    public Observable<List<Story>> refreshStories(Category category,int count) {
        return storiesManager.refreshStoriesByCategory(category, count);
    }

}
