package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.IStoriesByCategoryLoadingInteractor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public class StoriesLoadingInteractor extends StoriesInteractor implements IStoriesByCategoryLoadingInteractor {

    public StoriesLoadingInteractor(IStoriesManager storiesManager) {
        super(storiesManager);
    }

    @Override
    public Observable<List<Story>> loadStories(Category category,int count,int offset) {
        return storiesManager.getStoriesByCategory(category,count,offset);
        // warning: showing only last n number of items
        // TODO: 12.01.2017 Pagination
    }

}
