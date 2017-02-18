package com.valevich.clean.repository;


import java.util.List;

import rx.Observable;

public interface IRepository<T,Specification> {
    void add(Iterable<T> items);

    void add(T item);

    void update(T item);

    Observable<List<T>> read(Specification spec);
}
