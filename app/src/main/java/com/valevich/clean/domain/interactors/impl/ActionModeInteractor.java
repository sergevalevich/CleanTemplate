package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.actionmode.IActionModeHelper;
import com.valevich.clean.domain.interactors.IStorySelectedInteractor;
import com.valevich.clean.domain.model.Story;

public class ActionModeInteractor implements IStorySelectedInteractor {

    private IActionModeHelper<Story> actionModeHelper;

    public ActionModeInteractor(IActionModeHelper<Story> actionModeHelper) {
        this.actionModeHelper = actionModeHelper;
    }

    @Override
    public void onStorySelected(Story story) {
        actionModeHelper.start(story);
    }
}
