package com.valevich.clean.domain.repository;

import com.valevich.clean.domain.repository.base.Repository;

import rx.Observable;


public interface IMessageRepository extends Repository {
    Observable<String> getHelloMessage(String userName);
    Observable<String> getByeMessage(String userName);
}
