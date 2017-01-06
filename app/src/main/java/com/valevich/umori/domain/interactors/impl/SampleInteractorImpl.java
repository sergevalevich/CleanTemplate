package com.valevich.umori.domain.interactors.impl;


import com.valevich.umori.domain.executor.Executor;
import com.valevich.umori.domain.executor.MainThread;
import com.valevich.umori.domain.interactors.SampleInteractor;
import com.valevich.umori.domain.interactors.base.AbstractInteractor;
import com.valevich.umori.domain.repository.Repository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class SampleInteractorImpl extends AbstractInteractor implements SampleInteractor {

    private SampleInteractor.Callback mCallback;
    private Repository                mRepository;

    public SampleInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback, Repository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        // TODO: Implement this with your business logic
    }
}
