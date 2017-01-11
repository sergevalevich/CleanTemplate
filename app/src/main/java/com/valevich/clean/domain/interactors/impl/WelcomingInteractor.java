package com.valevich.clean.domain.interactors.impl;


import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.repository.IMessageRepository;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WelcomingInteractor implements IWelcomingInteractor<String> {

    private IMessageRepository mRepository;
    private String mUserName;

    public WelcomingInteractor(IMessageRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public Observable<String> buildUseCaseObservable() {
        return mRepository.getHelloMessage(mUserName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
