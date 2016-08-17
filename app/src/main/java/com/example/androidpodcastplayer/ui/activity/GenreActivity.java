package com.example.androidpodcastplayer.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.Podcast;
import com.example.androidpodcastplayer.model.Results;
import com.example.androidpodcastplayer.rest.ApiClient;
import com.example.androidpodcastplayer.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class GenreActivity extends AppCompatActivity{

    public static void launch(Activity activity, int genreId, String genreTitle) {
        Intent intent = new Intent(activity, GenreActivity.class);
        intent.putExtra(Constants.GENRE_ID, genreId);
        intent.putExtra(Constants.GENRE_TITLE, genreTitle);
        activity.startActivity(intent);
    }

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // instantiate the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }

        String genreTitle = getIntent().getStringExtra(Constants.GENRE_TITLE);
        if (genreTitle != null) {
            setTitle(genreTitle + " Genre");
        }

        // retrieve genreId and execute download task asynchronously
        int genreId = getIntent().getIntExtra(Constants.GENRE_ID, 0);
        if (genreId > 0) {
            executeCatalogueQuery(genreId);
        } else {
            Utils.showSnackbar(mLayout, "No podcasts found");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void executeCatalogueQuery(int genreId) {
        ApiInterface restService = ApiClient.getClient().create(ApiInterface.class);
        Call<Results> call = restService.getCategoryPodcasts(
                Constants.REST_TERM, genreId, Constants.REST_LIMIT
        );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                List<Podcast> results = response.body().getResults();
                for (Podcast result : results) {
                    Timber.i("%s: artist name: %s", Constants.LOG_TAG, result.getArtistName());
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Timber.e("%s error %s", Constants.LOG_TAG, t.getMessage());
                Utils.showSnackbar(mLayout, "Error executing download");
            }
        });
    }

}
