package com.valevich.clean.domain.interactors.impl;


import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Story;

import java.util.List;

abstract class StoriesInteractor {

    IStoriesManager storiesManager;

    StoriesInteractor(IStoriesManager storiesManager) {
        this.storiesManager = storiesManager;
    }

}
