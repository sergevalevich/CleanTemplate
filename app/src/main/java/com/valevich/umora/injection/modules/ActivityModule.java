package com.valevich.umora.injection.modules;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.valevich.umora.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @NonNull
    Activity providesActivity() {return activity;}

    @Provides @NonNull
    @ActivityContext
    Context providesContext() {return activity;}
}