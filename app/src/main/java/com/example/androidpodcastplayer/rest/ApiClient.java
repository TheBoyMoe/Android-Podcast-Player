package com.example.androidpodcastplayer.rest;

import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // example url: https://itunes.apple.com/search?term=podcasts&genreId=1406&limit=10
    private static final String BASE_URL = "https://itunes.apple.com/"; //FIXME
    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(getsOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    private static OkHttpClient getsOkHttpClient() {
        if (sOkHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            sOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor()) // enable network inspection
                    .build();
        }
        return sOkHttpClient;
    }


}
