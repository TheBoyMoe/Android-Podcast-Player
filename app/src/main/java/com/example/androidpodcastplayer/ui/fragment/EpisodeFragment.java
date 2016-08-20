package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.model.episode.Episode;
import com.example.androidpodcastplayer.model.episode.Feed;
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
                Timber.i("%s success!", Constants.LOG_TAG);
//                List<Episode> episodes = response.body().getChannel().getItems();
//                for (Episode episode:episodes) {
//                    Timber.i("%s: title: %s",
//                            Constants.LOG_TAG, episode.getTitle());
//                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Timber.e("%s failure, error: %s", Constants.LOG_TAG, t.getMessage());
                getContract().downloadError("Error downloading podcast feed");
            }
        });
    }

}
