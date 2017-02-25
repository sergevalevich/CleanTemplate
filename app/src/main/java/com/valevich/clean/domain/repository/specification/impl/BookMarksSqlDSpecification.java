package com.valevich.clean.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

import static android.R.attr.offset;

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
