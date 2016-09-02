package com.example.androidpodcastplayer.player.service;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.devbrackets.android.playlistcore.api.AudioPlayerApi;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.example.androidpodcastplayer.player.manager.PlaylistManager;
import com.example.androidpodcastplayer.player.model.AudioItem;


public class AudioService extends BasePlaylistService<AudioItem, PlaylistManager>{

    @Override
    protected int getNotificationId() {
        return 0;
    }

    @NonNull
    @Override
    protected PendingIntent getNotificationClickPendingIntent() {
        return null;
    }

    @Nullable
    @Override
    protected Bitmap getDefaultLargeNotificationImage() {
        return null;
    }

    @Override
    protected int getNotificationIconRes() {
        return 0;
    }

    @Override
    protected int getRemoteViewIconRes() {
        return 0;
    }

    @NonNull
    @Override
    protected AudioPlayerApi getNewAudioPlayer() {
        return null;
    }

    @Override
    protected float getAudioDuckVolume() {
        return 0;
    }

    @NonNull
    @Override
    protected PlaylistManager getPlaylistManager() {
        return null;
    }
}
