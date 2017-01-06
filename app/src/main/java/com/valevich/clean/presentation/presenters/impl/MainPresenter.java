package com.valevich.clean.presentation.presenters.impl;

import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.presentation.presenters.IMainPresenter;
import com.valevich.clean.presentation.presenters.base.AbstractPresenter;

public class MainPresenter extends AbstractPresenter implements IMainPresenter,
        IWelcomingInteractor.Callback {

    private IMainPresenter.View mView;

    public MainPresenter(Executor executor,
                         IMainThread mainThread,
                         View view) {
        super(executor, mainThread);
        mView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
