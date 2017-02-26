package com.valevich.umora.database.converters;


import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.model.Story;

import java.util.ArrayList;
import java.util.List;

public class DbStoryConverter {
    public static List<Story> getStoriesByDbEntity(List<StoryEntity> storyEntities) {
        List<Story> stories = new ArrayList<>(storyEntities.size());
        for (StoryEntity entity : storyEntities) {
            stories.add(new Story(
                    entity.text(),
                    entity.textLow(),
                    entity.site(),
                    entity.category_name(),
                    entity.isBookMarked(),
                    entity.bookMarkDate(),
                    entity.date()));
        }
        return stories;
    }

}
