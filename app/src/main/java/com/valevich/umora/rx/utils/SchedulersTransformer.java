package com.valevich.umora.rx.utils;


import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class SchedulersTransformer {


    private final Observable.Transformer schedulersTransformer =
            createTransformer();

    @Inject
    SchedulersTransformer () {

    }

    @SuppressWarnings("unchecked")
    public <T> Observable.Transformer<T, T> applySchedulers() {
        return schedulersTransformer;
    }

    private <T> Observable.Transformer<T, T> createTransformer() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
