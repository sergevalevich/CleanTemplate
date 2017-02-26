package com.valevich.umora;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.valevich.umora.injection.Injector;
import com.valevich.umora.injection.components.ApplicationComponent;
import com.valevich.umora.injection.components.DaggerApplicationComponent;
import com.valevich.umora.injection.modules.ApplicationModule;

import timber.log.Timber;

public class UmoraApplication extends Application {

    private Injector<ApplicationComponent> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isInAnalyzerProcess()) initLeakCanary();
        initTimber();
        initStetho();
        injector = new Injector<>(ApplicationComponent.class, createComponent());
    }

    public void inject(Object target) {
        injector.inject(target);
    }

    public Injector<ApplicationComponent> getInjector() {
        return injector;
    }

    private ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
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
