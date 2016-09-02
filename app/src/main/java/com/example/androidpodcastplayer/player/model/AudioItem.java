package com.example.androidpodcastplayer.player.model;


import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.player.manager.PlaylistManager;

public class AudioItem implements IPlaylistItem {

    private Item mEpisode;
    private String mImageUrl;

    public AudioItem(Item episode, String imageUrl) {
        mEpisode = episode;
        mImageUrl = imageUrl;
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
        return null; // TODO downloaded episode
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
        return mEpisode.getAuthor() != null ? Utils.htmlToStringParser(mEpisode.getAuthor()) : "unknown";
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
        String description = null;
        if (mEpisode.getSubtitle() != null && !mEpisode.getSubtitle().isEmpty()) {
            description = Utils.htmlToStringParser(mEpisode.getSubtitle());
        } else {
            description = mEpisode.getDescription() != null ? Utils.htmlToStringParser(mEpisode.getDescription()) : "unknown";
        }

        return description;
    }

    private String getEpisodeImageUrl() {
        String url = null;
        if (mImageUrl != null) {
            url = mImageUrl;
        } else {
            if (mEpisode.getImage() != null) {
                if (mEpisode.getImage().getUrl() != null && !mEpisode.getImage().getUrl().isEmpty()) {
                    url = mEpisode.getImage().getUrl();
                }
            }
        }
        return url;
    }

    private String getEpisodeTitle() {
        String title = null;
        if (mEpisode.getTitle() != null && !mEpisode.getTitle().isEmpty()) {
            title = Utils.htmlToStringParser(mEpisode.getTitle());
        } else {
            title = mEpisode.getAuthor() != null ? Utils.htmlToStringParser(mEpisode.getAuthor()) : "unknown";
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
