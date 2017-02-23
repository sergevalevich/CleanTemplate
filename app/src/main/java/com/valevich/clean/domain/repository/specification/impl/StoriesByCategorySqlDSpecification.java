package com.valevich.clean.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class StoriesByCategorySqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static StoriesByCategorySqlDSpecification create(Category category, int limit, int offset) {
        return new AutoValue_StoriesByCategorySqlDSpecification(category,limit,offset);
    }

    abstract Category category();
    abstract int limit();
    abstract int offset();

    @Override
    public SqlDelightStatement getStatement() {
        return StoryEntity.FACTORY.select_by_category(
                category().getSite(),
                category().getName(),
                limit(),
                offset());
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_by_categoryMapper();
    }
}
