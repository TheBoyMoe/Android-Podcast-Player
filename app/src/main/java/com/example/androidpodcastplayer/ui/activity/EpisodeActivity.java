package com.example.androidpodcastplayer.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.ui.fragment.EpisodeFragment;

public class EpisodeActivity extends BlankActivity implements
        EpisodeFragment.Contract{

    // impl contract methods
    @Override
    public void onNavigationIconBackPressed() {
        onBackPressed();
    }

    @Override
    public void downloadEpisode() {
        Utils.showSnackbar(mLayout, "download");
    }

    @Override
    public void addEpisodeToPlaylist() {
        Utils.showSnackbar(mLayout, "add to playlist");
    }
    // END

    private FrameLayout mLayout;

    public static void launch(Activity activity, Item episode) {
        Intent intent = new Intent(activity, EpisodeActivity.class);
        intent.putExtra(Constants.EPISODE_ITEM, episode);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = (FrameLayout) findViewById(R.id.fragment_container);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            Item episode = getIntent().getParcelableExtra(Constants.EPISODE_ITEM);
            if (episode != null)
                initFragment(EpisodeFragment.newInstance(episode));
        }

    }
}
