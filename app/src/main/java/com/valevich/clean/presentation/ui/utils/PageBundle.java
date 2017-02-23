package com.valevich.clean.presentation.ui.utils;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PageBundle<T> {

    public static <T> PageBundle<T> create(T data, int offset) {
        return new AutoValue_PageBundle<T>(data,offset);
    }

    public abstract T data();
    public abstract int offset();
}
