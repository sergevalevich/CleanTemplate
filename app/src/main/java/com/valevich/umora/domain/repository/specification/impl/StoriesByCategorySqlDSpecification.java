package com.valevich.umora.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class StoriesByCategorySqlDSpecification implements SqlDelightSpecification<StoryEntity>,Parcelable {

    public static StoriesByCategorySqlDSpecification create(Category category) {
        return new AutoValue_StoriesByCategorySqlDSpecification(category);
    }

    abstract Category category();

    @Override
    public SqlDelightStatement getStatement() {
        return StoryEntity.FACTORY.select_by_category(
                category().getSite(),
                category().getName());
    }

    @Override
    public RowMapper<StoryEntity> getMapper() {
        return StoryEntity.FACTORY.select_by_categoryMapper();
    }
}
