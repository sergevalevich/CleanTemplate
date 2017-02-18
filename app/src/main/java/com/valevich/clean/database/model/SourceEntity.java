package com.valevich.clean.database.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SourceEntity implements SourceModel,Entity {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    static final SourceModel.Factory<SourceEntity> FACTORY = new SourceModel.Factory<>(AutoValue_SourceEntity::new);
}
