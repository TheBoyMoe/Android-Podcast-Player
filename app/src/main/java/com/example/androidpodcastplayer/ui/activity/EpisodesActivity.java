package com.example.androidpodcastplayer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.model.podcast.Podcast;
import com.example.androidpodcastplayer.ui.fragment.EpisodesFragment;

public class EpisodesActivity extends BlankActivity implements
        EpisodesFragment.Contract{

    // impl of contract method
    @Override
    public void launchPlayer(Item episode) {
        EpisodeActivity.launch(this, episode);
    }

    @Override
    public void addEpisodeToPlaylist() {
        // TODO
    }

    @Override
    public void downloadEpisode() {
        // TODO
    }

    @Override
    public void downloadError(String message) {
        // TODO send message back to PodcastActivity to display snackbar
        finish();
    }

    @Override
    public void onNavigationIconBackPressed() {
        onBackPressed();
    }
    // END


    public static void launch(Activity activity, String feedUrl) {
        Intent intent = new Intent(activity, EpisodesActivity.class);
        intent.putExtra(Constants.RSS_FEED_URL, feedUrl);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, Podcast item) {
        Intent intent = new Intent(activity, EpisodesActivity.class);
        intent.putExtra(Constants.PODCAST_ITEM, item);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            // String feedUrl = getIntent().getStringExtra(Constants.RSS_FEED_URL);
            Podcast item = getIntent().getParcelableExtra(Constants.PODCAST_ITEM);
            if (item != null)
                initFragment(EpisodesFragment.newInstance(item));
        }
    }




}
