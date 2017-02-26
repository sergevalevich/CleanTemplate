package com.valevich.umora.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.repository.specification.impl.StoriesByCategorySqlDSpecification;
import com.valevich.umora.errors.NetworkUnavailableException;
import com.valevich.umora.injection.ApplicationContext;
import com.valevich.umora.network.UmoraApi;
import com.valevich.umora.network.converters.PayloadStoryConverter;
import com.valevich.umora.network.utils.ConnectivityInspector;
import com.valevich.umora.presentation.ui.fragments.StoriesByCategoryFragment;
import com.valevich.umora.rx.utils.SchedulersTransformer;

import javax.inject.Inject;

import icepick.State;
import rx.Observable;


public class StoriesByCategoryPresenter extends StoriesPresenter<StoriesByCategoryFragment> {

    private static final int REFRESH_STORIES_TASK_ID = 2;

    @State
    Category category;

    @State
    int storiesCount;

    @Inject
    UmoraApi umoraApi;

    @Inject
    @ApplicationContext Context context;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                REFRESH_STORIES_TASK_ID,
                () -> ConnectivityInspector.isNetworkAvailable(context)
                        ? umoraApi.getStories(category.getSite(), category.getName(), storiesCount)
                        .map(PayloadStoryConverter::getStoriesByPayload)
                        .doOnNext(getRepository()::add)
                        .compose(SchedulersTransformer.INSTANCE.applySchedulers())
                        : Observable.error(new NetworkUnavailableException()),
                (f,s) -> f.onStoriesRefreshed(),
                StoriesByCategoryFragment::onError);
    }

    public void refreshStoriesByCategory(Category category, int count) {
        this.category = category;
        this.storiesCount = count;
        start(REFRESH_STORIES_TASK_ID);
    }

    public void getStoriesByCategory(Category category) {
        loadStories(StoriesByCategorySqlDSpecification.create(category));
    }
}
