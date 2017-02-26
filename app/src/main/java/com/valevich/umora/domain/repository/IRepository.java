package com.valevich.umora.domain.repository;


import java.util.List;

import rx.Observable;

public interface IRepository<T,Specification> {
    void add(Iterable<T> items);

    void add(T item);

    Observable<T> update(T item);

    Observable<List<T>> get(Specification specification);
}
