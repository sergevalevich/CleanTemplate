package com.valevich.umora.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import nucleus.factory.PresenterFactory;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;
import timber.log.Timber;

public abstract class BaseFragment<P extends Presenter> extends NucleusSupportFragment<P> {

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedState) {
        Timber.d("onCreate %s",getClass().getSimpleName());
        setPresenterFactory(createPresenterFactory());
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume %s",getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("onStop %s",getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy %s",getClass().getSimpleName());
    }

    abstract PresenterFactory<P> createPresenterFactory();
}
