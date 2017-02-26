package com.valevich.umora.database.model;

import com.google.auto.value.AutoValue;
import com.valevich.clean.database.model.SourceModel;

@AutoValue
public abstract class SourceEntity implements SourceModel {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final SourceModel.Factory<SourceEntity> FACTORY = new SourceModel.Factory<>(AutoValue_SourceEntity::new);
}
