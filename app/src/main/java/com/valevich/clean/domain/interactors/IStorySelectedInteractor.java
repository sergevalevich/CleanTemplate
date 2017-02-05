package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Story;

public interface IStorySelectedInteractor extends Interactor {
    void onStorySelected(Story story);
}
