package com.example.androidpodcastplayer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.ui.fragment.EpisodeFragment;

public class EpisodeActivity extends BaseActivity implements
        EpisodeFragment.Contract{

    // impl of contract method
    @Override
    public void onItemClick(String episodeUrl) {
        // TODO
        Utils.showSnackbar(mLayout, "Clicked item");
    }

    @Override
    public void downloadError(String message) {
        // TODO send message back to PodcastActivity to display snackbar
        finish();
    }

    public static void launch(Activity activity, String feedUrl) {
        Intent intent = new Intent(activity, EpisodeActivity.class);
        intent.putExtra(Constants.RSS_FEED_URL, feedUrl);
        activity.startActivity(intent);
    }

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // instantiate the toolbar and add the up arrow
        initToolbar();

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            String feedUrl = getIntent().getStringExtra(Constants.RSS_FEED_URL);
            initFragment(EpisodeFragment.newInstance(feedUrl));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO save podcast to subscriptions
                Utils.showSnackbar(mLayout, "Clicked save podcast");
            }
        });
    }



}
