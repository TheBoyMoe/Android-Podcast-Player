package com.example.androidpodcastplayer.rest;


import com.example.androidpodcastplayer.model.podcast.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // download the podcasts for the genreId
    // example url: https://itunes.apple.com/search?term=podcasts&genreId=1406&limit=10&sort=recent
    @GET("search?")
    Call<Results> getGenrePodcasts(@Query("term") String term,
                                   @Query("genreId") int genreId,
                                   @Query("limit") int limit,
                                   @Query("sort") String sort);


}
