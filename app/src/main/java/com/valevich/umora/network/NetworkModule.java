package com.valevich.umora.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.valevich.umora.network.utils.MyAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "http://www.umori.li/api/";
    private static final int CONNECTION_TIME_OUT = 30;
    private static final int READ_TIME_OUT = 30;

    @Provides
    @Singleton
    static UmoraApi provideApi(Retrofit retrofit) {
        return retrofit.create(UmoraApi.class);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callFactory)
                .build();
    }

    @Provides
    @Singleton
    static Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    static CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    static Gson provideGson(TypeAdapterFactory factory) {
        return new GsonBuilder().registerTypeAdapterFactory(factory).create();
    }


    @Provides
    @Singleton
    static TypeAdapterFactory provideTypeAdapterFactory() {
        return MyAdapterFactory.create();
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();

    }

    @Provides
    @Singleton
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    static StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

}
