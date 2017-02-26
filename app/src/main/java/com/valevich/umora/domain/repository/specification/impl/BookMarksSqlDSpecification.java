package com.valevich.umora.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class BookMarksSqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static BookMarksSqlDSpecification create() {
        return new AutoValue_BookMarksSqlDSpecification();
    }

    @Override
    public SqlDelightStatement getStatement() {
        return new SqlDelightStatement(StoryEntity.SELECT_BOOKMARKS,new String[0],null);
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_bookmarksMapper();
    }
}
