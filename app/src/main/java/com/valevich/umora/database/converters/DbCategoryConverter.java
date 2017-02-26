package com.valevich.umora.database.converters;


import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

public class DbCategoryConverter {
    public static List<Category> getCategoriesByDbEntity(List<CategoryEntity> entities) {
        List<Category> categories = new ArrayList<>(entities.size());
        for (CategoryEntity categoryEntity : entities) {
            categories.add(new Category(
                    categoryEntity.name(),
                    categoryEntity.site(),
                    categoryEntity.description()));
        }
        return categories;
    }
}
