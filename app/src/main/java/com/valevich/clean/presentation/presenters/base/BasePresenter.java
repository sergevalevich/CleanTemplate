package com.valevich.clean.presentation.presenters.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import icepick.Icepick;
import nucleus.presenter.RxPresenter;
import nucleus.view.ViewWithPresenter;

public class BasePresenter<V extends ViewWithPresenter> extends RxPresenter<V> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);
    }

    @Override
    protected void onSave(@NonNull Bundle state) {
        super.onSave(state);
        Icepick.saveInstanceState(this, state);
    }

}
