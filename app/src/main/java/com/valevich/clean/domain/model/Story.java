package com.valevich.clean.domain.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Story implements Parcelable {

    public static final int DEFAULT_COUNT = 3;

    public static Story create(String text, String site, String category, Boolean isBookMarked) {
        return new AutoValue_Story(text, site, category, isBookMarked);
    }

    public abstract String text();

    public abstract String site();

    public abstract String categoryName();

    public abstract Boolean isBookMarked();
}
