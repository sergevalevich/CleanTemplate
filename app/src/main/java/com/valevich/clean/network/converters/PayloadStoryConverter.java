package com.valevich.clean.network.converters;


import android.text.Html;
import android.text.Spanned;

import com.valevich.clean.domain.model.Story;
import com.valevich.clean.network.model.StoryPayload;

import java.util.ArrayList;
import java.util.List;

public class PayloadStoryConverter {
    public static List<Story> getStoriesByPayload(List<StoryPayload> payload) {
        List<Story> stories = new ArrayList<>();
        for (StoryPayload storyPayload : payload) {
            stories.add(new Story(
                    fromHtml(storyPayload.elementPureHtml()),
                    storyPayload.site(),
                    storyPayload.name(),
                    false));
        }
        return stories;
    }

    @SuppressWarnings("deprecation")
    private static String fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result.toString();
    }
}
