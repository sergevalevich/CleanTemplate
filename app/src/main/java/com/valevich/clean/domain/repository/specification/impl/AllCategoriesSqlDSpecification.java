package com.valevich.clean.domain.repository.specification.impl;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

@AutoValue
public abstract class AllCategoriesSqlDSpecification implements SqlDelightSpecification<CategoryEntity>,Parcelable {

    public static AllCategoriesSqlDSpecification create() {
        return new AutoValue_AllCategoriesSqlDSpecification();
    }

    @Override
    public SqlDelightStatement getStatement() {
        return new SqlDelightStatement(CategoryEntity.SELECT_ALL,new String[0],null);
    }

    @Override
    public RowMapper<CategoryEntity> getMapper() {
        return CategoryEntity.FACTORY.select_allMapper();
    }
}
