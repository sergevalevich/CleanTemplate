package com.valevich.umora.database.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryEntity implements CategoryModel {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final CategoryModel.Factory<CategoryEntity> FACTORY = new CategoryModel.Factory<>(AutoValue_CategoryEntity::new);
}
