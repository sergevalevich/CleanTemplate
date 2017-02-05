package com.valevich.clean.events;

import com.valevich.clean.domain.model.Story;

public class StoryChangedEvent {
    private Story story;

    public StoryChangedEvent(Story story) {
        this.story = story;
    }

    public Story getStory() {
        return story;
    }
}
