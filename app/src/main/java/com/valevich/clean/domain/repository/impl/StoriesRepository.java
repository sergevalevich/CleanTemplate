package com.valevich.clean.domain.repository.impl;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.converters.DbStoryConverter;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.domain.repository.IStoriesRepository;
import com.valevich.clean.errors.NetworkUnavailableException;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadStoryConverter;
import com.valevich.clean.network.utils.ConnectivityInspector;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;
import timber.log.Timber;


public class StoriesRepository implements IStoriesRepository {

    private Context context;
    private DatabaseHelper databaseHelper;
    private RestService restService;

    private final StoryEntity.Update_row rowUpdateStatement;
    private final StoryEntity.Insert_row rowInsertStatement;

    public StoriesRepository(DatabaseHelper databaseHelper, RestService restService, Context context) {
        this.context = context;
        this.databaseHelper = databaseHelper;
        this.restService = restService;
        this.rowUpdateStatement = new StoryEntity.Update_row(databaseHelper.getWritableDatabase());
        this.rowInsertStatement = new StoryEntity.Insert_row(databaseHelper.getWritableDatabase());
    }

    @Override
    public void add(Iterable<Story> items) {
        BriteDatabase.Transaction transaction = databaseHelper.startTransaction();
        try {
            for (Story story : items) {
                add(story);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        Timber.d("Stories inserted");
    }

    @Override
    public void add(Story story) {
        rowInsertStatement.bind(story.getText(),
                story.getTextLow(),
                story.getSite(),
                story.getCategoryName(),
                story.getText(),
                story.getDate());
        databaseHelper.insert(StoryEntity.TABLE_NAME, rowInsertStatement.program);
    }

    @Override
    public Observable<Story> update(Story story) {
        return Observable.defer(() -> {
            Timber.d("Updating story on %s", Thread.currentThread().getName());
            rowUpdateStatement.bind(story.isBookMarked(),story.getText());
            databaseHelper.updateDelete(StoryEntity.TABLE_NAME,rowUpdateStatement.program);
            return Observable.just(story);
        });
    }

    @Override
    public Observable<List<Story>> getByCategory(Category category, int limit, int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_by_category(category.getSite(), category.getName(), limit, offset);
        return databaseHelper.get(StoryEntity.TABLE_NAME, statement.statement, statement.args, StoryEntity.FACTORY.select_by_categoryMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Story>> getByCategory(Category category, int limit) {
        if (!ConnectivityInspector.isNetworkAvailable(context)) {
            return Observable.error(new NetworkUnavailableException());
        } else {
            return restService.getStories(category.getSite(), category.getName(), limit)
                    .map(PayloadStoryConverter::getStoriesByPayload)
                    .doOnNext(this::add)
                    .compose(SchedulersTransformer.INSTANCE.applySchedulers());
        }

    }

    @Override
    public Observable<List<Story>> getBookMarked(int limit, int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_bookmarks(limit,offset);
        return databaseHelper.get(StoryEntity.TABLE_NAME, statement.statement,statement.args,StoryEntity.FACTORY.select_bookmarksMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Story>> find(String filter, int count, int offset) {
        SqlDelightStatement statement = StoryEntity.FACTORY.select_by_filter("%" + filter.toLowerCase() + "%",count,offset);
        return databaseHelper.get(StoryEntity.TABLE_NAME, statement.statement, statement.args,
                StoryEntity.FACTORY.select_by_filterMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

}
