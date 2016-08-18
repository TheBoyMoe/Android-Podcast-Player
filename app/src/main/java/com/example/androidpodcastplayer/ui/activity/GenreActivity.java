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
import com.example.androidpodcastplayer.ui.fragment.GenreFragment;

import timber.log.Timber;


public class GenreActivity extends AppCompatActivity implements
        GenreFragment.Contract{

    // impl of contract methods
    @Override
    public void onItemClick() {
        // TODO download episodes for that particular podcast
    }

    @Override
    public void downloadError(String message) {
        Utils.showSnackbar(mLayout, message);
    }
    // END

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

        // instantiate the toolbar with up nav arrow
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }

        // set the title to match the genre
        String genreTitle = getIntent().getStringExtra(Constants.GENRE_TITLE);
        if (genreTitle != null) {
            setTitle(genreTitle + " Genre");
        }
        // retrieve the genreId and load the genre fragment
        int genreId = getIntent().getIntExtra(Constants.GENRE_ID, 0);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, GenreFragment.newInstance(genreId))
                    .commit();
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



}
