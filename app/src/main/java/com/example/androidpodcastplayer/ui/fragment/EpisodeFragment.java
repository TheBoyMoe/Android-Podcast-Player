package com.example.androidpodcastplayer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.episode.Item;

public class EpisodeFragment extends ContractFragment<EpisodeFragment.Contract>{

    public interface Contract {
        void onNavigationIconBackPressed();
        void downloadEpisode();
        void addEpisodeToPlaylist();
    }

    private ProgressBar mProgressBar;
    private TextView mEpisodeTitle;
    private TextView mEpisodeDescription;
    private ImageView mEpisodeThumbnail;
    private AppCompatSeekBar mSeekBar;
    private ImageButton mPlayButton;
    private ImageButton mStopButton;

    public EpisodeFragment() {}

    public static EpisodeFragment newInstance(Item episode, String imageUrl) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EPISODE_ITEM, episode);
        args.putString(Constants.PODCAST_IMAGE, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_episode, container, false);
        initToolbar(view);
        setupView(view);
        Item episode = getArguments().getParcelable(Constants.EPISODE_ITEM);
        String imageUrl = getArguments().getString(Constants.PODCAST_IMAGE);
        populateView(episode, imageUrl);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_episode, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                getContract().downloadEpisode();
                return true;
            case R.id.action_playlist:
                getContract().addEpisodeToPlaylist();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // helper methods
    private void initToolbar(View view) {
        // instantiate the toolbar with up nav arrow
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);
            actionBar.setTitle("");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getContract().onNavigationIconBackPressed();
                }
            });
        }
    }

    private void setupView(View view) {
        mEpisodeThumbnail = (ImageView) view.findViewById(R.id.episode_thumbnail);
        mEpisodeTitle = (TextView) view.findViewById(R.id.episode_title);
        mEpisodeDescription = (TextView) view.findViewById(R.id.episode_description);
        mSeekBar = (AppCompatSeekBar) view.findViewById(R.id.episode_progress_bar);
        mPlayButton = (ImageButton) view.findViewById(R.id.action_play);
        mStopButton = (ImageButton) view.findViewById(R.id.action_stop);
    }

    private void populateView(Item episode, String imageUrl) {
        if (episode != null) {
            mEpisodeTitle.setText(episode.getTitle() != null ? episode.getTitle() : "");
            mEpisodeDescription.setText(episode.getDescription() != null ? episode.getDescription() : "");
            if (imageUrl != null) { // use the full size image
                Utils.loadPreviewWithGlide(getActivity(), imageUrl, mEpisodeThumbnail);
            } else {
                if (episode.getImage() != null) {
                    if (episode.getImage().getUrl() != null) {
                        Utils.loadPreviewWithGlide(getActivity(), episode.getImage().getUrl(), mEpisodeThumbnail);
                    }
                } else {
                    Utils.loadPreviewWithGlide(getActivity(), R.drawable.no_image_600x600, mEpisodeThumbnail);
                }
            }
        }
    }

}
