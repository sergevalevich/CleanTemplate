package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IStoriesByCategoryRefreshingInteractor extends Interactor {
    Observable<List<Story>> refreshStories(Category category, int count);
}
