package com.example.androidpodcastplayer.player.model;


import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.model.podcast.Podcast;
import com.example.androidpodcastplayer.player.manager.PlaylistManager;

import static com.example.androidpodcastplayer.common.Utils.htmlToStringParser;

public class AudioItem implements IPlaylistItem {

    private Item mEpisode;
    private Podcast mPodcast;

    public AudioItem(Item episode, Podcast podcast) {
        mEpisode = episode;
        mPodcast = podcast;
    }


    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getPlaylistId() {
        return 0;
    }

    @Override
    public int getMediaType() {
        return PlaylistManager.AUDIO;
    }

    @Override
    public String getMediaUrl() {
        return getFeed();
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return getEpisodeImageUrl();
    }

    @Override
    public String getArtworkUrl() {
        return getEpisodeImageUrl();
    }

    @Override
    public String getTitle() {
        return getEpisodeTitle();
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public String getArtist() {
        String author = "unknown";
        if (mEpisode != null && mEpisode.getAuthor() != null && !mEpisode.getAuthor().isEmpty()) {
            author = Utils.htmlToStringParser(mEpisode.getAuthor());
        }
        else if (mPodcast != null && mPodcast.getArtistName() != null && !mPodcast.getArtistName().isEmpty()) {
            author = htmlToStringParser(mPodcast.getArtistName());
        }
        return author;
    }

    public String getDescription() {
        return getEpisodeDescription();
    }

    public int getLength() {
        int length = 0;
        if (mEpisode.getEnclosure() != null) {
            if (mEpisode.getEnclosure().getLength() != null && !mEpisode.getEnclosure().getLength().isEmpty()) {
                length = Integer.valueOf(mEpisode.getEnclosure().getLength());
            }
        }
        return length;
    }

    // helper methods
    private String getEpisodeDescription() {
        String description = "unknown";
        if (mEpisode != null && mEpisode.getSubtitle() != null && !mEpisode.getSubtitle().isEmpty()) {
            description = htmlToStringParser(mEpisode.getSubtitle());
        } else if (mEpisode != null && mEpisode.getDescription() != null && !mEpisode.getDescription().isEmpty()){
            description = htmlToStringParser(mEpisode.getDescription());
        } else if (mPodcast != null && mPodcast.getCollectionName() != null && !mPodcast.getCollectionName().isEmpty()){
            description = Utils.htmlToStringParser(mPodcast.getCollectionName());
        }
        return description;
    }

    private String getEpisodeImageUrl() {
        String url = null;
        if (mPodcast != null && mPodcast.getArtworkUrl600() != null && !mPodcast.getArtworkUrl600().isEmpty()) {
            url = mPodcast.getArtworkUrl600();
        }
        else if (mEpisode.getImage() != null && mEpisode.getImage().getUrl() != null && !mEpisode.getImage().getUrl().isEmpty()) {
            url = mEpisode.getImage().getUrl();
        }
        return url;
    }

    private String getEpisodeTitle() {
        String title = "unknown";
        if (mEpisode != null && mEpisode.getTitle() != null && !mEpisode.getTitle().isEmpty()) {
            title = htmlToStringParser(mEpisode.getTitle());
        }
        else if (mPodcast != null && mPodcast.getTrackName() != null && !mPodcast.getTrackName().isEmpty()) {
            title = Utils.htmlToStringParser(mPodcast.getTrackName());
        }
        else if (mPodcast != null && mPodcast.getCollectionName() != null && !mPodcast.getCollectionName().isEmpty()) {
            title = Utils.htmlToStringParser(mPodcast.getCollectionName());
        }
        return title;
    }

    private String getFeed() {
        String feed = null;
        if (mEpisode.getEnclosure() != null) {
            if (mEpisode.getEnclosure().getUrl() != null && !mEpisode.getEnclosure().getUrl().isEmpty()) {
                feed = mEpisode.getEnclosure().getUrl();
            }
        }
        return feed;
    }

}
