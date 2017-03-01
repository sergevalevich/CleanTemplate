package com.valevich.umora.domain.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.umora.database.DatabaseHelper;
import com.valevich.umora.database.converters.DbStoryConverter;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.model.Story;
import com.valevich.umora.domain.repository.IRepository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;
import com.valevich.umora.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;


public class StoriesRepository implements IRepository<Story,SqlDelightSpecification<StoryEntity>> {
    private DatabaseHelper databaseHelper;

    private final StoryEntity.Update_row rowUpdateStatement;
    private final StoryEntity.Insert_row rowInsertStatement;

    private SchedulersTransformer schedulersTransformer;

    public StoriesRepository(DatabaseHelper databaseHelper,
                             StoryEntity.Update_row rowUpdateStatement,
                             StoryEntity.Insert_row rowInsertStatement,
                             SchedulersTransformer schedulersTransformer) {
        this.databaseHelper = databaseHelper;
        this.rowUpdateStatement = rowUpdateStatement;
        this.rowInsertStatement = rowInsertStatement;
        this.schedulersTransformer = schedulersTransformer;
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
    }

    @Override
    public void add(Story story) {
        rowInsertStatement.bind(story.getText(),
                story.getTextLow(),
                story.getSite(),
                story.getCategoryName(),
                story.getText(),
                story.getDate(),
                story.getText());
        databaseHelper.insert(StoryEntity.TABLE_NAME, rowInsertStatement.program);
    }

    @Override
    public Observable<Story> update(Story story) {
        return Observable.defer(() -> {
            rowUpdateStatement.bind(story.isBookMarked(),story.getBookMarkDate(),story.getText());
            databaseHelper.updateDelete(StoryEntity.TABLE_NAME,rowUpdateStatement.program);
            return Observable.just(story);
        }).compose(schedulersTransformer.applySchedulers());
    }

    @Override
    public Observable<List<Story>> get(SqlDelightSpecification<StoryEntity> specification) {
        SqlDelightStatement statement = specification.getStatement();
        return databaseHelper.get(StoryEntity.TABLE_NAME, statement.statement, statement.args, specification.getMapper())
                .map(DbStoryConverter::getStoriesByDbEntity)
                .compose(schedulersTransformer.applySchedulers());
    }
}
