package com.example.androidpodcastplayer.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.ui.fragment.PodcastFragment;


public class PodcastActivity extends BaseActivity implements
        PodcastFragment.Contract{

    // impl of contract methods
    @Override
    public void onItemClick(String feedUrl) {
        // launch EpisodesActivity and display Podcast Info and episode list
        if (Utils.isClientConnected(this)) {
            if (feedUrl.contains(Constants.FEED_BURNER_BASE_URL)) {
                Utils.showSnackbar(mLayout, getString(R.string.feed_not_available));
            } else {
                EpisodesActivity.launch(this, feedUrl);
            }
        } else {
            Utils.showSnackbar(mLayout, getString(R.string.no_network_connection));
        }
    }

    @Override
    public void downloadError(String message) {
        Utils.showSnackbar(mLayout, message);
    }
    // END

    public static void launch(Activity activity, int genreId, String genreTitle) {
        Intent intent = new Intent(activity, PodcastActivity.class);
        intent.putExtra(Constants.GENRE_ID, genreId);
        intent.putExtra(Constants.GENRE_TITLE, genreTitle);
        activity.startActivity(intent);
    }

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // instantiate the toolbar with up nav arrow
        initToolbar();

        // set the title to match the genre
        String genreTitle = getIntent().getStringExtra(Constants.GENRE_TITLE);
        if (genreTitle != null) {
            setTitle(genreTitle + " Genre");
        }

        // retrieve the genreId and load the genre fragment
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            int genreId = getIntent().getIntExtra(Constants.GENRE_ID, 0);
            initFragment(PodcastFragment.newInstance(genreId));
        }

    }


}
