package com.example.androidpodcastplayer.rest;

import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssClient {

    private static Retrofit sRetrofit;

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(Utils.getsOkHttpClient())
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }



}
