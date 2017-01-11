package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.interactors.IGoodByeInteractor;
import com.valevich.clean.domain.repository.IMessageRepository;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodByeInteractor implements IGoodByeInteractor<String> {

    private IMessageRepository mRepository;
    private String mUserName;

    public GoodByeInteractor(IMessageRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public Observable<String> buildUseCaseObservable() {
        return mRepository.getByeMessage(mUserName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
