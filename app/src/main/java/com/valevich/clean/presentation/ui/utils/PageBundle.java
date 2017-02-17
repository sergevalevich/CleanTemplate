package com.valevich.clean.presentation.ui.utils;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PageBundle {

    public static PageBundle create(Object data, int offset) {
        return new AutoValue_PageBundle(data,offset);
    }

    public abstract Object data();
    public abstract int offset();
}
