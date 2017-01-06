package com.valevich.umori.presentation.presenters.impl;

import com.valevich.umori.domain.executor.Executor;
import com.valevich.umori.domain.executor.MainThread;
import com.valevich.umori.domain.interactors.SampleInteractor;
import com.valevich.umori.presentation.presenters.MainPresenter;
import com.valevich.umori.presentation.presenters.base.AbstractPresenter;

/**
 * Created by dmilicic on 12/13/15.
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        SampleInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
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
