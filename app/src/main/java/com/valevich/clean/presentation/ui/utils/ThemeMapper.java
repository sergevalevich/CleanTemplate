package com.valevich.clean.presentation.ui.utils;

import android.support.annotation.StyleRes;
import android.util.SparseIntArray;

import com.valevich.clean.R;

public class ThemeMapper {

    private static SparseIntArray themes;

    static {
        themes = new SparseIntArray();
        themes.append(1, R.style.Dark);
        themes.append(2,R.style.Light);
    }

    public static @StyleRes int getTheme(int id) {
        return themes.get(id);
    }
}
