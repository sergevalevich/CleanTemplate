package com.valevich.clean.presentation.presenters.base;


import android.os.Bundle;

import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;
import com.valevich.clean.presentation.ui.base.BaseView;

import icepick.Icepick;

public abstract class RecoverablePresenter<T extends BaseView> extends AbstractPresenter implements IRecoverablePresenter<T>{

    private IRecoverablePresenter.Callback mCallback;

    public RecoverablePresenter(Executor executor, IMainThread mainThread,IRecoverablePresenter.Callback callback) {
        super(executor, mainThread);
        mCallback = callback;
    }

    @Override
    public void save(Bundle state) {
        Icepick.saveInstanceState(this,state);
    }

    @Override
    public void restore(Bundle state) {
        Icepick.restoreInstanceState(this, state);
        mCallback.onRestored();
    }
}
