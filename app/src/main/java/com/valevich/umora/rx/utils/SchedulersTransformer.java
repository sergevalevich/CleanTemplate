package com.valevich.umora.rx.utils;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public enum SchedulersTransformer {

    //if i make an instance for each interactor
    //every instance will live as long as presenter lives
    // 2 interactors - 2 instances until process restart/user leaves
    // singleton - one instance until process restart/user leaves
    // + avoiding code duplication
    INSTANCE;

    private final Observable.Transformer schedulersTransformer =
            createTransformer();

    @SuppressWarnings("unchecked")
    public <T> Observable.Transformer<T, T> applySchedulers() {
        return schedulersTransformer;
    }

    private <T> Observable.Transformer<T, T> createTransformer() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
