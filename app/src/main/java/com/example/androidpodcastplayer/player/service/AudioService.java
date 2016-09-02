package com.example.androidpodcastplayer.player.service;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.devbrackets.android.playlistcore.api.AudioPlayerApi;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.example.androidpodcastplayer.player.manager.PlaylistManager;
import com.example.androidpodcastplayer.player.model.AudioItem;


public class AudioService extends BasePlaylistService<AudioItem, PlaylistManager>{

    private static final int NOTIFICATION_ID = 101;
    private static final int FOREGROUND_REQUEST_CODE = 102;
    private static final float AUDIO_DUCK_VOLUME = 0.1f;

    private NotificationTarget mNotificationTarget = new NotificationTarget();
    private LockScreenTarget mLockScreenTarget = new LockScreenTarget();

    private Bitmap mDefaultLargeNotificationImage;
    private Bitmap mLargeNotificationImage;
    private Bitmap mLockScreenArtwork;

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


    @Override
    protected void performOnMediaCompletion() {
        performNext();
        immediatelyPause = false;
    }



    // ensure the correct notification image is loaded
    private class NotificationTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap) {
            mLargeNotificationImage = bitmap;
            onLargeNotificationImageUpdated();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mLargeNotificationImage = null;
        }

        @Override
        public void onPrepareLoad(Drawable placeholderDrawable) {
            // no-op
        }
    }

    // ensure the correct lockscreen image is loaded
    private class LockScreenTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap) {
            mLockScreenArtwork = bitmap;
            onRemoteViewArtworkUpdated();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mLockScreenArtwork = null;
        }

        @Override
        public void onPrepareLoad(Drawable placeholderDrawable) {
            // no-op
        }
    }


}
