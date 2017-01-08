package com.valevich.clean.domain.interactors.impl;


import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.interactors.base.AbstractInteractor;
import com.valevich.clean.domain.repository.IMessageRepository;

public class WelcomingInteractor extends AbstractInteractor implements IWelcomingInteractor {

    private IWelcomingInteractor.Callback mCallback;
    private IMessageRepository mRepository;

    public WelcomingInteractor(Executor threadExecutor,
                               IMainThread mainThread,
                               Callback callback,
                               IMessageRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    private void notifyError() {
        mMainThread.post(() -> mCallback.onRetrievalFailed("Nothing to welcome you with :("));
    }

    private void postMessage(final String msg) {
        //execute onMessageRetrieved on UiThread
        mMainThread.post(() -> mCallback.onMessageRetrieved(msg));
    }

    //for executor
    @Override
    public void run() {

        // retrieve the message
        final String message = mRepository.getMessage();

        // check if we have failed to retrieve our message
        if (message == null || message.length() == 0) {

            // notify the failure on the main thread
            notifyError();

            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        postMessage(message);
    }
}
