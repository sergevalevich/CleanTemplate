package com.valevich.umora.database.model;

import com.google.auto.value.AutoValue;
import com.valevich.clean.database.model.StoryModel;

@AutoValue
public abstract class StoryEntity implements StoryModel {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final Factory<StoryEntity> FACTORY = new Factory<>(AutoValue_StoryEntity::new);
}
