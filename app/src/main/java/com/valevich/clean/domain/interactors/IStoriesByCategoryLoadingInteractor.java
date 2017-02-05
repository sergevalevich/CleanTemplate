package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IStoriesByCategoryLoadingInteractor extends Interactor {
    Observable<List<Story>> loadStories(Category category,int count);
}
