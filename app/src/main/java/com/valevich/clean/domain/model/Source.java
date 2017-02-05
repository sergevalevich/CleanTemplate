package com.valevich.clean.domain.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Source implements Parcelable {
    public static Source create(String site,List<Category> categories) {
        return new AutoValue_Source(site,categories);
    }
    public abstract String site();
    public abstract List<Category> categories();
}
