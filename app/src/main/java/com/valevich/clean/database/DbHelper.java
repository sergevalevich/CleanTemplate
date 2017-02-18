package com.valevich.clean.database;


import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqldelight.RowMapper;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.database.model.CategoryModel;
import com.valevich.clean.database.model.SourceEntity;
import com.valevich.clean.database.model.SourceModel;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.database.model.StoryModel;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.errors.UpdateDeleteException;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DbHelper {

    private BriteDatabase db;

    public DbHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        db = sqlBrite.wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    public Observable<List<StoryEntity>> getStories(String query, String[] args, RowMapper<StoryEntity> mapper) {
        return db.createQuery(StoryEntity.TABLE_NAME, query, args).mapToList(mapper::map);
    }

    public Observable<List<CategoryEntity>> getCategories(String query, String[] args, RowMapper<CategoryEntity> mapper) {
        return db.createQuery(CategoryEntity.TABLE_NAME, query, args).mapToList(mapper::map);
    }

    public void insertStories(List<Story> stories) {
        Timber.d("Inserting %d stories",stories.size());
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            for (int i = 0; i < stories.size(); i++) {
                Story story = stories.get(i);
                insertStory(story);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        Timber.d("Stories inserted");
    }

    public void insertSources(List<Source> sources) {
        Timber.d("inserting sources on %s", Thread.currentThread().getName());
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            for (int i = 0; i < sources.size(); i++) {
                Source source = sources.get(i);
                insertSource(source);
                for(Category category:source.getCategories()) insertCategory(category);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    public Observable<Boolean> updateStory(Story story) {
        return Observable.defer(() -> {
            Timber.d("Updating story on %s", Thread.currentThread().getName());
            StoryEntity.Update_row statement = new StoryModel.Update_row(db.getWritableDatabase());
            statement.bind(story.isBookMarked(),story.getText());
            int rows = db.executeUpdateDelete(StoryEntity.TABLE_NAME, statement.program);
            return rows == 0
                    ? Observable.error(new UpdateDeleteException())
                    : Observable.just(story.isBookMarked());
        });
    }

    private void insertSource (Source source) {
        SourceEntity.Insert_row statement = new SourceModel.Insert_row(db.getWritableDatabase());
        statement.bind(source.getSite());
        db.executeInsert(SourceEntity.TABLE_NAME,statement.program);
    }

    private void insertCategory(Category category) {
        CategoryEntity.Insert_row statement = new CategoryModel.Insert_row(db.getWritableDatabase());
        statement.bind(category.getName(), category.getDescription(), category.getSite());
        db.executeInsert(CategoryEntity.TABLE_NAME, statement.program);
    }

    private void insertStory(Story story) {
        StoryEntity.Insert_row statement = new StoryModel.Insert_row(db.getWritableDatabase());
        statement.bind(story.getText(), story.getTextLow(), story.getSite(),story.getCategoryName(),story.getText(),story.getDate());
        db.executeInsert(StoryEntity.TABLE_NAME, statement.program);
    }
}
