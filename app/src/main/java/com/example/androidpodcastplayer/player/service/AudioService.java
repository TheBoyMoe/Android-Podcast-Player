package com.example.androidpodcastplayer.player.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.devbrackets.android.exomedia.EMAudioPlayer;
import com.devbrackets.android.playlistcore.api.AudioPlayerApi;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.example.androidpodcastplayer.PodcastPlayerApplication;
import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.player.manager.PlaylistManager;
import com.example.androidpodcastplayer.player.model.AudioItem;
import com.example.androidpodcastplayer.ui.activity.EpisodesActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import timber.log.Timber;


public class AudioService extends BasePlaylistService<AudioItem, PlaylistManager>{

    private static final int NOTIFICATION_ID = 101;
    private static final int FOREGROUND_REQUEST_CODE = 102;
    private static final float AUDIO_DUCK_VOLUME = 0.1f;

    private NotificationTarget mNotificationTarget = new NotificationTarget();
    private LockScreenTarget mLockScreenTarget = new LockScreenTarget();

    private Bitmap mDefaultLargeNotificationImage;
    private Bitmap mLargeNotificationImage;
    private Bitmap mLockScreenArtwork;
    private Picasso mPicasso;

    @Override
    public void onCreate() {
        super.onCreate();
        mPicasso = Picasso.with(getApplicationContext());
        // Timber.i("LOG Service onCreate called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Timber.i("LOG Service onDestroy called");
    }

    @Override
    protected void onServiceCreate() {
        super.onServiceCreate();
        Timber.i("LOG Service onServiceCreated called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Timber.i("LOG Service onStartCommand called, flag: %d, startId: %d", flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onMediaStopped(AudioItem playlistItem) {
        super.onMediaStopped(playlistItem);
        // Timber.i("LOG Service onMediaStopped called");
    }

    @Override
    protected void onMediaPlaybackStarted(AudioItem playlistItem, long currentPosition, long duration) {
        super.onMediaPlaybackStarted(playlistItem, currentPosition, duration);
        // Timber.i("LOG Service onMediaPlayBackStarted called");
    }

    @Override
    protected void onMediaPlaybackEnded() {
        super.onMediaPlaybackEnded();
        // Timber.i("LOG Service onMediaPlaybackEnded called");
    }

    @Override
    protected int getNotificationId() {
        return NOTIFICATION_ID;
    }

    @NonNull
    @Override
    protected PendingIntent getNotificationClickPendingIntent() {
        Intent intent = new Intent(getApplicationContext(), EpisodesActivity.class);
        return PendingIntent.getActivity(getApplicationContext(),
                FOREGROUND_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Nullable
    @Override
    protected Bitmap getDefaultLargeNotificationImage() {
        if (mDefaultLargeNotificationImage == null) {
            mDefaultLargeNotificationImage =
                BitmapFactory.decodeResource(getResources(), R.drawable.no_image_600x600);
        }
        return mDefaultLargeNotificationImage;
    }

    @Nullable
    @Override
    protected Bitmap getDefaultLargeNotificationSecondaryImage() {
        return null;
    }

    @Override
    protected void updateLargeNotificationImage(int size, AudioItem playlistItem) {
        mPicasso.load(playlistItem.getThumbnailUrl()).into(mNotificationTarget);
    }

    @Override
    protected void updateRemoteViewArtwork(AudioItem playlistItem) {
        mPicasso.load(playlistItem.getArtworkUrl()).into(mLockScreenTarget);
    }

    @Nullable
    @Override
    protected Bitmap getRemoteViewArtwork() {
        return mLockScreenArtwork;
    }

    @Nullable
    @Override
    public Bitmap getLargeNotificationImage() {
        return mLargeNotificationImage;
    }

    @Override
    protected int getNotificationIconRes() {
        // FIXME
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int getRemoteViewIconRes() {
        // FIXME
        return R.mipmap.ic_launcher;
    }

    @NonNull
    @Override
    protected AudioPlayerApi getNewAudioPlayer() {
        return new AudioApi(new EMAudioPlayer(getApplicationContext()));
    }

    @Override
    protected float getAudioDuckVolume() {
        return AUDIO_DUCK_VOLUME;
    }

    @NonNull
    @Override
    protected PlaylistManager getPlaylistManager() {
        return PodcastPlayerApplication.getsPlaylistManager();
    }

    @Override
    protected void performOnMediaCompletion() {
        performNext();
        immediatelyPause = false;
    }



    // ensure the correct notification image is loaded
    private class NotificationTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
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
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
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
