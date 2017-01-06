package com.valevich.clean.domain.interactors.impl;


import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.interactors.base.AbstractInteractor;
import com.valevich.clean.domain.repository.Repository;

public class WelcomingInteractor extends AbstractInteractor implements IWelcomingInteractor {

    private IWelcomingInteractor.Callback mCallback;
    private Repository mRepository;

    public WelcomingInteractor(Executor threadExecutor,
                               IMainThread mainThread,
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
