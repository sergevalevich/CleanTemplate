package com.valevich.umora.network.converters;


import com.valevich.umora.domain.model.Source;
import com.valevich.umora.network.model.CategoryPayload;

import java.util.ArrayList;
import java.util.List;

public class PayloadSourcesConverter {
    public static List<Source> getSourcesByPayload(List<List<CategoryPayload>> sourcesPayload) {
        List<Source> sources = new ArrayList<>(sourcesPayload.size());
        for (List<CategoryPayload> source:sourcesPayload) {
            sources.add(new Source(
                    source.get(0).site(),
                    PayloadCategoryConverter.getCategoriesByPayload(source)));
        }
        return sources;
    }
}
