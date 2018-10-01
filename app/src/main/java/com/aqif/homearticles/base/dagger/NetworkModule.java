package com.aqif.homearticles.base.dagger;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Base dependencies required to make an API call; normally used by repositories.
 *
 */

@Module
public class NetworkModule {

    public final String API_BASE_URL = "https://api-mobile.home24.com/api/v1/";

    @Reusable
    @Provides
    public Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                                           RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                                           OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .baseUrl(API_BASE_URL)
                .build();
        return retrofit;
    }

    @Reusable
    @Provides
    public OkHttpClient provideOkHttp(HttpLoggingInterceptor loggingInterceptor){
        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();
        return okHttpClient;
    }
    @Reusable
    @Provides
    public HttpLoggingInterceptor provideLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Reusable
    @Provides
    public GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Reusable
    @Provides
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

}
