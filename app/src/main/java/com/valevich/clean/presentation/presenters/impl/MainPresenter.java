package com.valevich.clean.presentation.presenters.impl;

import com.valevich.clean.domain.executor.Executor;
import com.valevich.clean.domain.executor.IMainThread;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.interactors.impl.WelcomingInteractor;
import com.valevich.clean.domain.repository.impl.MessageRepository;
import com.valevich.clean.presentation.presenters.IMainPresenter;
import com.valevich.clean.presentation.presenters.base.IRecoverablePresenter;
import com.valevich.clean.presentation.presenters.base.RecoverablePresenter;
import com.valevich.clean.presentation.ui.MainView;

import icepick.State;
import timber.log.Timber;

public class MainPresenter extends RecoverablePresenter<MainView> implements
        IMainPresenter<MainView>,
        IWelcomingInteractor.Callback,
        IRecoverablePresenter.Callback {

    @State String mLogin;

    @State String mPassword;

    private MainView mView;

    private String mMessage;


    public MainPresenter(Executor executor,
                         IMainThread mainThread,
                         MainView view) {
        super(executor, mainThread,this);
        mView = view;
    }

    @Override
    public void login(String login,String password) {
        Timber.d("Credentials: %s %s",login,password);
        mLogin = login;
        mPassword = password;
        mView.showProgress();
        // initialize the interactor
        WelcomingInteractor interactor = new WelcomingInteractor(
                mExecutor,
                mMainThread,
                this,
                new MessageRepository()
        );
        // run the interactor
        interactor.execute();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onRestored() {
        login(mLogin,mPassword);
    }

    @Override
    public void setView(MainView view) {
        mView = view;
        publish();
    }

    @Override
    public void onMessageRetrieved(String message) {
        mMessage = message;
        publish();
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView != null) mView.hideProgress();
        onError(error);
    }

    private void publish() {
        if (mView != null) {
            mView.hideProgress();
            if (mMessage != null)
                mView.displayMessage(mMessage);
        }
    }
}
