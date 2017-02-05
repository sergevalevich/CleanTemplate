package com.valevich.clean.database.converters;


import com.valevich.clean.database.model.StoryEntity;
import com.valevich.clean.domain.model.Story;

import java.util.ArrayList;
import java.util.List;

public class DbStoryConverter {
    public static List<Story> getStoriesByDbEntity(List<StoryEntity> storyEntities) {
        List<Story> stories = new ArrayList<>();
        for (StoryEntity entity : storyEntities) {
            stories.add(
                    Story.create(entity.text(),
                            entity.site(),
                            entity.category_name(),
                            entity.isBookMarked())
            );
        }
        return stories;
    }

}
