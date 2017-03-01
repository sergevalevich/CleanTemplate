package com.valevich.umora.presentation.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.valevich.umora.R;
import com.valevich.umora.UmoraApplication;
import com.valevich.umora.injection.Injector;
import com.valevich.umora.injection.components.ActivityComponent;
import com.valevich.umora.injection.modules.ActivityModule;
import com.valevich.umora.presentation.ui.utils.ThemeMapper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    SharedPreferences preferences;

    @Inject
    ThemeMapper themeMapper;

    private Injector<ActivityComponent> injector;

    public void inject(Object target) {
        injector.inject(target);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        injector = new Injector<>(ActivityComponent.class, createComponent());
        injector.inject(this);
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
        int themeId = Integer.parseInt(preferences.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_default)));
        setTheme(themeMapper.getTheme(themeId));
    }

    private ActivityComponent createComponent() {
        return ((UmoraApplication) getApplication())
                .getInjector()
                .getComponent()
                .plus(new ActivityModule(this));
    }

    abstract int getLayoutRes();
}
