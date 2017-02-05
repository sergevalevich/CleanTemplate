package com.valevich.clean.database.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class StoryEntity implements StoryModel {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final Factory<StoryEntity> FACTORY = new Factory<>(AutoValue_StoryEntity::new);
}
