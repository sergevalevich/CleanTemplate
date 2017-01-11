package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;


public interface IGoodByeInteractor<T> extends Interactor<T> {
    void setUserName(String userName);
}
