package com.valevich.clean.domain.data.impl;

import android.content.Context;

import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.DbHelper;
import com.valevich.clean.database.converters.DbStoryConverter;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.errors.NetworkUnavailableException;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadStoryConverter;
import com.valevich.clean.network.utils.ConnectivityInspector;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;

public class StoriesManager implements IStoriesManager {

    private DbHelper dbHelper;
    private RestService restService;
    private Context context;

    public StoriesManager(DbHelper dbHelper, RestService restService, Context context) {
        this.dbHelper = dbHelper;
        this.restService = restService;
        this.context = context;
    }

    // TODO: 14.02.2017 Change to site,name,count
    @Override
    public Observable<List<Story>> getStoriesByCategory(Category category, int count,int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_by_category(category.getSite(),category.getName(),count,offset);
        return dbHelper.getStories(statement.statement,
                statement.args,
                StoryEntity.FACTORY.select_by_categoryMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Story>> refreshStoriesByCategory(Category category, int count) {
        return getFreshStoriesByCategory(category.getSite(),category.getName(), count)
                .doOnNext(this::cacheStories)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<Boolean> update(Story story) {
        return dbHelper.updateStory(story)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Story>> getBookMarks(int count, int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_bookmarks(count,offset);
        return dbHelper.getStories(statement.statement,statement.args,StoryEntity.FACTORY.select_bookmarksMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Story>> findStories(String filter,int count, int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_by_filter("%" + filter.toLowerCase() + "%",count,offset);
        return dbHelper.getStories(statement.statement,
                statement.args,
                StoryEntity.FACTORY.select_by_filterMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    private Observable<List<Story>> getFreshStoriesByCategory(String site, String name, int count) {
        return ConnectivityInspector.isNetworkAvailable(context)
                ? restService.getStories(site, name, count).map(PayloadStoryConverter::getStoriesByPayload)
                : Observable.error(new NetworkUnavailableException());
    }

    private void cacheStories(List<Story> stories) {
        dbHelper.insertStories(stories);
    }

}
