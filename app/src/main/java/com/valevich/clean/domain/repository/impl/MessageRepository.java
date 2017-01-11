package com.valevich.clean.domain.repository.impl;

import com.valevich.clean.domain.repository.IMessageRepository;

import rx.Observable;
import timber.log.Timber;

public class MessageRepository implements IMessageRepository {
    @Override
    public Observable<String> getHelloMessage(String userName) {
        return Observable.defer(() -> {
            Timber.d("getting Message... %s",userName);
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Observable.just("Welcome, " + userName);
        });
    }

    @Override
    public Observable<String> getByeMessage(String userName) {
        return Observable.defer(() -> {
            Timber.d("getting Message... %s",userName);
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Observable.just("Bye, " + userName);
        });
    }
}
