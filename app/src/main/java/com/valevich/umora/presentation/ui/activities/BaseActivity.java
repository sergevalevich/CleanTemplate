package com.valevich.umora.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;

import com.valevich.umora.R;
import com.valevich.umora.presentation.ui.utils.ThemeMapper;

import butterknife.ButterKnife;
import icepick.Icepick;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

public abstract class BaseActivity<P extends Presenter> extends NucleusAppCompatActivity<P> {

    @Override
    protected void onCreate(Bundle savedState) {
        setTheme();
        super.onCreate(savedState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedState);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void setTheme() {
        int themeId = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_default)));
        setTheme(ThemeMapper.getTheme(themeId));
    }

    abstract int getLayoutRes();
}
