package com.valevich.clean.presentation.presenters.impl;

import android.os.Bundle;

import com.valevich.clean.domain.interactors.IGoodByeInteractor;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.interactors.impl.GoodByeInteractor;
import com.valevich.clean.domain.interactors.impl.WelcomingInteractor;
import com.valevich.clean.domain.repository.IMessageRepository;
import com.valevich.clean.domain.repository.impl.MessageRepository;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.activities.MainActivity;

import icepick.State;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainActivity> {

    private static final int HELLO_MESSAGE_TASK_ID = 1;

    private static final int BYE_MESSAGE_TASK_ID = 2;

    @State
    String mUserName;

    private IMessageRepository mMessageRepository = new MessageRepository();

    private IWelcomingInteractor<String> mWelcomingInteractor = new WelcomingInteractor(mMessageRepository);

    private IGoodByeInteractor<String> mGoodByeInteractor = new GoodByeInteractor(mMessageRepository);

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        if(savedState != null) {
            Timber.d("Restoring name");
            mWelcomingInteractor.setUserName(mUserName);
            mGoodByeInteractor.setUserName(mUserName);
        }

        restartableLatestCache(
                HELLO_MESSAGE_TASK_ID,
                () -> mWelcomingInteractor.buildUseCaseObservable(),
                MainActivity::onMessageReceived,
                MainActivity::onError);

        restartableLatestCache(
                BYE_MESSAGE_TASK_ID,
                () -> mGoodByeInteractor.buildUseCaseObservable(),
                MainActivity::onMessageReceived,
                MainActivity::onError);
    }

    public void loadHelloMessage(String userName) {
        mUserName = userName;
        mWelcomingInteractor.setUserName(mUserName);
        start(HELLO_MESSAGE_TASK_ID);
    }

    public void loadByeMessage(String userName) {
        mUserName = userName;
        mGoodByeInteractor.setUserName(mUserName);
        start(BYE_MESSAGE_TASK_ID);
    }

}
