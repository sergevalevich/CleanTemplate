package com.valevich.umora.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class CategoryPayload {

    public static TypeAdapter<CategoryPayload> typeAdapter(Gson gson) {
        return new AutoValue_CategoryPayload.GsonTypeAdapter(gson);
    }

    public abstract String site();
    public abstract String name();
    public abstract String desc();
}
