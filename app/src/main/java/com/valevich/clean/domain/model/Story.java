package com.valevich.clean.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Story {
    static Story create(String sourceName, String sourceDesc, String content) {
        return new AutoValue_Story(sourceName,sourceDesc,content);
    }
    abstract String sourceName();
    abstract String sourceDesc();
    abstract String content();
}
