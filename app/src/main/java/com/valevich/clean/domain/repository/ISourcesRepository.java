package com.valevich.clean.domain.repository;


import com.valevich.clean.domain.model.Source;

import java.util.List;

import rx.Observable;

public interface ISourcesRepository {
    void add(Iterable<Source> items);

    void add(Source item);

    Observable<List<Source>> get();
}
