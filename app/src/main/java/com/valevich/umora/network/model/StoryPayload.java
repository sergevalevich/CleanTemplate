package com.valevich.umora.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class StoryPayload {
    // The public static method returning a TypeAdapter<Foo> is what
    // tells auto-value-gson to create a TypeAdapter for Foo.
    public static TypeAdapter<StoryPayload> typeAdapter(Gson gson) {
        return new AutoValue_StoryPayload.GsonTypeAdapter(gson);
    }
    public abstract String site();
    public abstract String name();
    public abstract String desc();
    public abstract String elementPureHtml();
}
