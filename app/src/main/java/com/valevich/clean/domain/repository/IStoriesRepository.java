package com.valevich.clean.domain.repository;

import com.valevich.clean.domain.model.Story;
import com.valevich.clean.domain.repository.base.Repository;

import rx.Observable;


public interface IStoriesRepository extends Repository {
    Observable<Story> getStories(String site, String category, int count);
}
