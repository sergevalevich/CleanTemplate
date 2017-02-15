package com.valevich.clean.repository;


import com.valevich.clean.repository.specifications.Specification;

import java.util.List;

public interface IRepository<T> {
    void add(Iterable<T> items);

    void update(T item);

    List<T> read(Specification specification);
}
