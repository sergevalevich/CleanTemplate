package com.valevich.clean.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valevich.clean.network.model.CategoryPayload;
import com.valevich.clean.network.model.StoryPayload;
import com.valevich.clean.network.utils.MyAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RestService {

    private static final String BASE_URL = "http://www.umori.li/api/";
    private static final int CONNECTION_TIME_OUT = 30;
    private static final int READ_TIME_OUT = 30;

    private UmoriliApi api;

    public RestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(UmoriliApi.class);
    }

    public Observable<List<StoryPayload>> getStories(String site, String name, int count) {
        return api.getStories(site, name, count);
    }

    public Observable<List<List<CategoryPayload>>> getSources(){
        return api.getSources();
    }

    public Observable<List<StoryPayload>> getRandom(int count) {
        return api.getRandom(count);
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private HttpLoggingInterceptor getInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private Gson getGson() {
        return new GsonBuilder().registerTypeAdapterFactory(MyAdapterFactory.create()).create();
    }
}
