package com.valevich.clean;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class UmoriApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isInAnalyzerProcess()) initLeakCanary();
        initTimber();
        initStetho();
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetwork = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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
