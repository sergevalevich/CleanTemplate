package com.valevich.clean;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class UmoriApplication extends Application {

    //// TODO: 04.01.2017 Add ViewModel

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isInAnalyzerProcess()) initLeakCanary();
        initTimber();
        initStetho();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initLeakCanary() {
        LeakCanary.install(this);
    }

    private boolean isInAnalyzerProcess() {
        return LeakCanary.isInAnalyzerProcess(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
