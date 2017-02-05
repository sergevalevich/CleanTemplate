package com.valevich.clean.database.converters;


import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DbCategoryConverter {
    public static List<Category> getCategoriesByDbEntity(List<CategoryEntity> entities) {
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : entities) {
            categories.add(Category.create(
                    categoryEntity.name(),
                    categoryEntity.site(),
                    categoryEntity.description()));
        }
        return categories;
    }
}
