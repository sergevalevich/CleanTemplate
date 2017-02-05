package com.valevich.clean.domain.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Category implements Parcelable{
    public static Category create(String name, String site, String description) {
        return new AutoValue_Category(name,site,description);
    }
    public abstract String name();
    public abstract String site();
    public abstract String description();
}
