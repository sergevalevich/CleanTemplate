package com.valevich.umora.network.converters;


import com.valevich.umora.domain.model.Category;
import com.valevich.umora.network.model.CategoryPayload;

import java.util.ArrayList;
import java.util.List;

public class PayloadCategoryConverter {
    public static List<Category> getCategoriesByPayload(List<CategoryPayload> payload) {
        List<Category> categories = new ArrayList<>(payload.size());
        for (CategoryPayload categoryPayload : payload) {
            categories.add(new Category(
                    categoryPayload.name(),
                    categoryPayload.site(),
                    categoryPayload.desc()));
        }
        return categories;
    }
}
