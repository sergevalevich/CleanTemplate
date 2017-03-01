package com.valevich.umora.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.valevich.umora.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConnectivityInspector {

    private Context context;

    @Inject
    ConnectivityInspector(@ApplicationContext Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
