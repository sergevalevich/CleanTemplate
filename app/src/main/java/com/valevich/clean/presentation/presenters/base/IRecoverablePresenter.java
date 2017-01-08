package com.valevich.clean.presentation.presenters.base;


import android.os.Bundle;

import com.valevich.clean.presentation.ui.base.BaseView;

public interface IRecoverablePresenter<T extends BaseView> extends BasePresenter<T> {

    interface Callback {
        void onRestored();
    }

    void save(Bundle state);
    void restore(Bundle state);
}
