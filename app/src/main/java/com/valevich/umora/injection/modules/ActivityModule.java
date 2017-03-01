package com.valevich.umora.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.valevich.umora.errors.ErrorMessageFactory;
import com.valevich.umora.errors.IErrorMessageFactory;
import com.valevich.umora.injection.ActivityContext;
import com.valevich.umora.injection.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides @NonNull
    FragmentActivity providesActivity() {return activity;}

    @Provides @NonNull
    @ActivityContext
    Context providesContext() {return activity;}

    @Provides
    IErrorMessageFactory provideErrorMessageFactory() {
        return new ErrorMessageFactory(activity);
    }

    @PerActivity
    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }
}