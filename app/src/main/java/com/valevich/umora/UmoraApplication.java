package com.valevich.umora;


import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.valevich.umora.injection.components.ApplicationComponent;
import com.valevich.umora.injection.components.DaggerApplicationComponent;
import com.valevich.umora.injection.modules.ApplicationModule;
import com.valevich.umora.utils.ReleaseTree;

import java.lang.ref.WeakReference;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class UmoraApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //if (!isInAnalyzerProcess()) initLeakCanary();
        initFabric();
        initTimber();
        initStetho();
    }

    public static UmoraApplication get(WeakReference<Context> contextWeakReference) {
        return (UmoraApplication) contextWeakReference.get().getApplicationContext();
    }

    public ApplicationComponent getAppComponent() {
        if (applicationComponent == null) applicationComponent = createComponent();
        return applicationComponent;
    }

    private ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initFabric() {
        Fabric.with(this, new Crashlytics());
    }

    private void initTimber() {
        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new ReleaseTree());
    }

    private void initLeakCanary() {
        LeakCanary.install(this);
    }

//    private boolean isInAnalyzerProcess() {
//        return LeakCanary.isInAnalyzerProcess(this);
//    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
