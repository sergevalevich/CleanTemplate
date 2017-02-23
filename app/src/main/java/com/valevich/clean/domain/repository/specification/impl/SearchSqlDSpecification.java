package com.valevich.clean.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

import static android.R.attr.category;
import static android.R.attr.filter;
import static android.R.attr.offset;

@AutoValue
public abstract class SearchSqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static SearchSqlDSpecification create(String query,int limit,int offset) {
        return new AutoValue_SearchSqlDSpecification(query,limit,offset);
    }

    abstract String query();
    abstract int limit();
    abstract int offset();

    @Override
    public SqlDelightStatement getStatement() {
        return StoryEntity.FACTORY
                .select_by_filter("%" + query().toLowerCase() + "%",limit(),offset());
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_by_filterMapper();
    }
}
