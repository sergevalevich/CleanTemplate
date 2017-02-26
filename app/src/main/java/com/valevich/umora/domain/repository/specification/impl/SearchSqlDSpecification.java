package com.valevich.umora.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class SearchSqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static SearchSqlDSpecification create(String query) {
        return new AutoValue_SearchSqlDSpecification(query);
    }

    abstract String query();

    @Override
    public SqlDelightStatement getStatement() {
        return StoryEntity.FACTORY.select_by_filter("%" + query().toLowerCase() + "%");
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_by_filterMapper();
    }
}
