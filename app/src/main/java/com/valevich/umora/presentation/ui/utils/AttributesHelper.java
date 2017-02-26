package com.valevich.umora.presentation.ui.utils;


import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class AttributesHelper {
    public static int getColorAttribute(Context context, int attrId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }
}
