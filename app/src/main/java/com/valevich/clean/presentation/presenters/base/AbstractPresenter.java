package com.valevich.clean.presentation.presenters.base;


import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;

/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and IMainThread objects that are needed for running interactors in a background thread.
 */
public abstract class AbstractPresenter {
    protected Executor mExecutor;
    protected IMainThread mMainThread;

    public AbstractPresenter(Executor executor, IMainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
