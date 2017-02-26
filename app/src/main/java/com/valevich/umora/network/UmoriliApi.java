package com.valevich.umora.network;

import com.valevich.umora.network.model.CategoryPayload;
import com.valevich.umora.network.model.StoryPayload;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface UmoriliApi {
    @GET("get")
    Observable<List<StoryPayload>> getStories(@Query("site") String site,
                                       @Query("name") String name,
                                       @Query("num") int count);

    @GET("sources")
    Observable<List<List<CategoryPayload>>> getSources();
}
