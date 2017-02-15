package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IStoriesSearchingInteractor extends Interactor {
    Observable<List<Story>> loadStories(String filter,int count, int offset);
}
