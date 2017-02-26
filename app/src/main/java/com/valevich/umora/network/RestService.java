package com.valevich.umora.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valevich.umora.network.model.CategoryPayload;
import com.valevich.umora.network.model.StoryPayload;
import com.valevich.umora.network.utils.MyAdapterFactory;

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

    private UmoraApi api;

    public RestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(UmoraApi.class);
    }

    public Observable<List<StoryPayload>> getStories(String site, String name, int count) {
        return api.getStories(site, name, count)
                //.delay(1, TimeUnit.SECONDS) // FIXME: 25.02.2017
                ;
    }

//    public Observable<List<Story>> getDummyStories(int offset) {
//        List<Story> dummyStories = new ArrayList<>();
//        List<Story> stories = new ArrayList<>();
//        for (int i = 0; i<Story.DEFAULT_COUNT; i++) {
//            String text = "dummy" + String.valueOf(i);
//            dummyStories.add(new Story(text,text,"bash.im","bash",false,i));
//        }
//        Timber.d("offset is %d",offset);
//        if (offset == 2 || offset >= Story.DEFAULT_COUNT) offset = 0;
//
//        for (int i = offset; i<10 + offset; i++) {
//            stories.add(dummyStories.get(i));
//            Timber.d("%s",dummyStories.get(i));
//        }
//        return Observable.just(stories);
//    }

    public Observable<List<List<CategoryPayload>>> getSources(){
        return api.getSources()
                //.delay(1, TimeUnit.SECONDS) // FIXME: 25.02.2017
                ;
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
