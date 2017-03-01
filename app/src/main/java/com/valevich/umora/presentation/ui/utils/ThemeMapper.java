package com.valevich.umora.presentation.ui.utils;

import android.support.annotation.StyleRes;
import android.util.SparseIntArray;

import com.valevich.umora.R;
import com.valevich.umora.injection.PerActivity;

import javax.inject.Inject;


@PerActivity
public class ThemeMapper {

    private SparseIntArray themes;

    @Inject
    public ThemeMapper() {
        themes = new SparseIntArray();
        themes.append(1, R.style.Dark);
        themes.append(2,R.style.Light);
    }

    public @StyleRes int getTheme(int id) {
        return themes.get(id);
    }
}
