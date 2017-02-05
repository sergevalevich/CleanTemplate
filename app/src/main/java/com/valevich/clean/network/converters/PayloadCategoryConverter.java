package com.valevich.clean.network.converters;


import com.valevich.clean.domain.model.Category;
import com.valevich.clean.network.model.CategoryPayload;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PayloadCategoryConverter {
    public static List<Category> getCategoriesByPayload(List<CategoryPayload> payload) {
        List<Category> categories = new ArrayList<>();
        for (CategoryPayload categoryPayload : payload) {
            categories.add(Category.create(
                    categoryPayload.name(),
                    categoryPayload.site(),
                    categoryPayload.desc()));
        }
        return categories;
    }
}
