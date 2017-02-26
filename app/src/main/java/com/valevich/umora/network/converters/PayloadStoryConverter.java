package com.valevich.umora.network.converters;


import android.text.Html;
import android.text.Spanned;

import com.valevich.umora.domain.model.Story;
import com.valevich.umora.network.model.StoryPayload;

import java.util.ArrayList;
import java.util.List;

public class PayloadStoryConverter {
    public static List<Story> getStoriesByPayload(List<StoryPayload> payload) {
        List<Story> stories = new ArrayList<>(payload.size());
        long date = System.currentTimeMillis();
        for (StoryPayload storyPayload : payload) {
            String text = fromHtml(storyPayload.elementPureHtml());
            stories.add(new Story(
                    text,
                    text.toLowerCase(),
                    storyPayload.site(),
                    storyPayload.name(),
                    false,
                    0,
                    date));
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
