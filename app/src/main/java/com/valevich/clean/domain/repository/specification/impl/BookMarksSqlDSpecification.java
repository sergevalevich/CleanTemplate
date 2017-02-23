package com.valevich.clean.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class BookMarksSqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static BookMarksSqlDSpecification create(int limit, int offset) {
        return new AutoValue_BookMarksSqlDSpecification(limit,offset);
    }

    abstract int limit();
    abstract int offset();

    @Override
    public SqlDelightStatement getStatement() {
        return StoryEntity.FACTORY.select_bookmarks(limit(),offset());
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_bookmarksMapper();
    }
}
