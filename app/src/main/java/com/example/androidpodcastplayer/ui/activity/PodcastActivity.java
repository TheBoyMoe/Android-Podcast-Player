package com.example.androidpodcastplayer.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.podcast.Podcast;
import com.example.androidpodcastplayer.ui.fragment.PodcastFragment;

import java.util.ArrayList;
import java.util.Locale;


public class PodcastActivity extends BaseActivity implements
        PodcastFragment.Contract{

    // impl of contract methods
    @Override
    public void onItemClick(Podcast item) {
        // launch EpisodesActivity and display Podcast Info and episode list
        if (Utils.isClientConnected(this)) {
            if (item.getFeedUrl().contains(Constants.FEED_BURNER_BASE_URL)) {
                Utils.showSnackbar(mLayout, getString(R.string.feed_not_available));
            } else {
                EpisodesActivity.launch(this, item);
            }
        } else {
            Utils.showSnackbar(mLayout, getString(R.string.no_network_connection));
        }
    }
    // END

    public static void launch(Activity activity, ArrayList<Podcast> list, String title, boolean isSearch) {
        Intent intent = new Intent(activity, PodcastActivity.class);
        intent.putParcelableArrayListExtra(Constants.PODCAST_LIST, list);
        intent.putExtra(Constants.PODCAST_TITLE, title);
        intent.putExtra(Constants.PODCAST_SEARCH, isSearch);
        activity.startActivity(intent);
    }

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // instantiate the toolbar with up nav arrow and set page title
        initToolbar();
        boolean isSearch = getIntent().getBooleanExtra(Constants.PODCAST_SEARCH, false);
        String title = getIntent().getStringExtra(Constants.PODCAST_TITLE);
        if (title != null) {
            if (isSearch) {
                setTitle(String.format(Locale.ENGLISH, "Results for: %s", title));
            } else {
                setTitle(String.format(Locale.ENGLISH, "%s genre", title));
            }
        }

        // retrieve the genreId and load the genre fragment
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            ArrayList<Podcast> list = getIntent().getParcelableArrayListExtra(Constants.PODCAST_LIST);
            initFragment(PodcastFragment.newInstance(list));
        }

    }


}
