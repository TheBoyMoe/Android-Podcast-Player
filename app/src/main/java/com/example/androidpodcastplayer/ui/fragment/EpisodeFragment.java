package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.episode.Category;
import com.example.androidpodcastplayer.model.episode.Channel;
import com.example.androidpodcastplayer.model.episode.Feed;
import com.example.androidpodcastplayer.model.episode.Image;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.rest.RssClient;
import com.example.androidpodcastplayer.rest.RssInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class EpisodeFragment extends ContractFragment<EpisodeFragment.Contract>{

    public interface Contract {
        void onItemClick(String podcastName);
        void downloadError(String message);
        void onNavigationIconBackPressed();
    }

    private CoordinatorLayout mLayout;
    private String mPodcastName;

    public EpisodeFragment() {}

    public static EpisodeFragment newInstance(String feedUrl) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putString(Constants.RSS_FEED_URL, feedUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_episode, container, false);
        mLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        initToolbar(view);
        initFab(view);

        Timber.i("%s feed: %s", Constants.LOG_TAG, getArguments().getString(Constants.RSS_FEED_URL));
        executeEpisodeQuery(getArguments().getString(Constants.RSS_FEED_URL));

        return view;
    }

    // download the podcast episode list
    private void executeEpisodeQuery(String feedUrl) {
        Timber.i("%s execute download", Constants.LOG_TAG);
        RssInterface rssService = RssClient.getClient().create(RssInterface.class);
        Call<Feed> call = rssService.getItems(feedUrl);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Channel channel = response.body().getChannel();
                mPodcastName = channel.getTitle();
                List<Image> images = channel.getImages();
                String url = null;
                for (Image image : images) {
                    url = image.getUrl();
                    if (url != null) break;
                }
                String cat = null;
                List<Category> categories = channel.getCategory();
                for (Category category : categories) {
                    cat = category.getCategory();
                    if (cat != null) break;
                }
                Timber.i("%s title: %s, pubDate: %s, buildDate: %s, language: %s, author: %s, description: %s, image link %s, category %s, episodes %s",
                    Constants.LOG_TAG, channel.getTitle(), channel.getPubDate(), channel.getBuildDate(), channel.getLanguage(),
                        channel.getAuthor(), channel.getDescription(), url, cat, channel.getItemList().size());

                // episode list
                List<Item> episodes = channel.getItemList();
                if (episodes != null && episodes.size() > 0) {
                    for (int i = 0; i < 5; i++) {
                        Item episode = episodes.get(i);
                        String imageUrl = null;
                        if (episode.getImage() != null) {
                            imageUrl = episode.getImage().getUrl();
                        }

                        Timber.i("%s title: %s, pubDate: %s, duration: %s, description: %s, " +
                                "author: %s, imageUrl: %s, episodeLength: %s, episodeType: %s, episodeUrl: %s",
                                Constants.LOG_TAG, episode.getTitle(), episode.getPubDate(),
                                episode.getDuration(), episode.getDescription(), episode.getAuthor(),
                                imageUrl, episode.getEpisodeInfo().getLength(),
                                episode.getEpisodeInfo().getType(), episode.getEpisodeInfo().getUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Timber.e("%s failure, error: %s", Constants.LOG_TAG, t.getMessage());
                getContract().downloadError("Error downloading podcast feed");
            }

        });
    }


    // helper methods
    private void initToolbar(View view) {
        // instantiate the toolbar with up nav arrow
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getContract().onNavigationIconBackPressed();
                }
            });
        }
    }

    private void initFab(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPodcastName != null) {
                    // TODO save the podcast to the playlist
                    // getContract().onItemClick(mPodcastName);
                    Utils.showSnackbar(mLayout, mPodcastName);
                }
            }
        });
    }


}
