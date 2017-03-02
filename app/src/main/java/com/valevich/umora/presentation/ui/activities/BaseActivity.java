package com.valevich.umora.presentation.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.valevich.umora.R;
import com.valevich.umora.UmoraApplication;
import com.valevich.umora.injection.components.ActivityComponent;
import com.valevich.umora.injection.modules.ActivityModule;
import com.valevich.umora.presentation.ui.utils.ThemeMapper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    SharedPreferences preferences;

    @Inject
    ThemeMapper themeMapper;

    private ActivityComponent activityComponent;

    private WeakReference<Context> weakReference;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = createComponent();
        }
        return activityComponent;
    }

    public WeakReference <Context> get() {
        return weakReference;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setTheme();
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedState);
    }

    @Override
    protected void onDestroy() {
        weakReference.clear();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void setTheme() {
        int themeId = Integer.parseInt(preferences.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_default)));
        setTheme(themeMapper.getTheme(themeId));
    }

    private ActivityComponent createComponent() {
        if (weakReference == null) weakReference = new WeakReference<>(this);
        return UmoraApplication.get(weakReference)
                .getAppComponent()
                .plus(new ActivityModule(this));
    }

    abstract int getLayoutRes();
}
