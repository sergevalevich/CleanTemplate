package com.valevich.clean.database.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryEntity implements CategoryModel,Entity {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final CategoryModel.Factory<CategoryEntity> FACTORY = new CategoryModel.Factory<>(AutoValue_CategoryEntity::new);
}
