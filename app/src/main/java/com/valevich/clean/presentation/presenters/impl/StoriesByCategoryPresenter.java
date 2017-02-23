package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.repository.specification.impl.StoriesByCategorySqlDSpecification;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadStoryConverter;
import com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import icepick.State;


public class StoriesByCategoryPresenter extends StoriesPresenter<StoriesByCategoryFragment> {

    private static final int REFRESH_STORIES_TASK_ID = 2;

    @State
    Category category;

    @State
    int storiesCount;

    private RestService restService;

    public StoriesByCategoryPresenter(Context context) {
        super(context);
        this.restService = new RestService();
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                REFRESH_STORIES_TASK_ID,
                // TODO: 22.02.2017 check connectivity
                () -> restService.getStories(category.getSite(),category.getName(),storiesCount)
                        .map(PayloadStoryConverter::getStoriesByPayload)
                        .doOnNext(getRepository()::add)
                        .compose(SchedulersTransformer.INSTANCE.applySchedulers()),
                (f,s) -> f.onStoriesRefreshed(),
                StoriesByCategoryFragment::onError);
    }

    public void refreshStoriesByCategory(Category category, int count) {
        this.category = category;
        this.storiesCount = count;
        start(REFRESH_STORIES_TASK_ID);
    }

    public void getStoriesByCategory(Category category, int count, int offset) {
        loadStories(StoriesByCategorySqlDSpecification.create(category,count,offset));
    }
}
