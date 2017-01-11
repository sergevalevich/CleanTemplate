package com.valevich.clean.domain.interactors.base;

import rx.Observable;

public interface Interactor<T> {
    Observable<T> buildUseCaseObservable();
}
