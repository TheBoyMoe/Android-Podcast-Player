package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.model.episode.Author;
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
        void onItemClick(String episodeUrl);
        void downloadError(String message);
    }

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

        Timber.i("%s feed: %s", Constants.LOG_TAG, getArguments().getString(Constants.RSS_FEED_URL));
        executeEpisodeQuery(getArguments().getString(Constants.RSS_FEED_URL));
        // end point changes each time

        return null;
    }

    private void executeEpisodeQuery(String feedUrl) {
        Timber.i("%s execute download", Constants.LOG_TAG);
        RssInterface rssService = RssClient.getClient().create(RssInterface.class);
        Call<Feed> call = rssService.getItems(feedUrl);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Channel channel = response.body().getChannel();

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

}
