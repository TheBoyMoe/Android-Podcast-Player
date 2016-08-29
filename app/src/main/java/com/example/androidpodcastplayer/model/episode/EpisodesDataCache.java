package com.example.androidpodcastplayer.model.episode;


import com.example.androidpodcastplayer.model.podcast.Podcast;

public class EpisodesDataCache {

    private static EpisodesDataCache sInstance;
    private Podcast mPodcast;
    private Channel mChannel;

    private EpisodesDataCache() {}

    public static EpisodesDataCache getInstance() {
        if (sInstance == null) {
            sInstance = new EpisodesDataCache();
        }
        return sInstance;
    }

    public Podcast getPodcast() {
        return mPodcast;
    }

    public void setPodcast(Podcast podcast) {
        mPodcast = podcast;
    }

    public Channel getChannel() {
        return mChannel;
    }

    public void setChannel(Channel channel) {
        mChannel = channel;
    }
}
