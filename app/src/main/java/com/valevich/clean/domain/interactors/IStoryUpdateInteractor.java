package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Story;

import rx.Observable;


public interface IStoryUpdateInteractor extends Interactor {
    Observable<Boolean> updateStory(Story story);
}
