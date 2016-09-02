package com.example.androidpodcastplayer.player.service;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.devbrackets.android.exomedia.EMAudioPlayer;
import com.devbrackets.android.playlistcore.api.AudioPlayerApi;

public class AudioApi extends BaseMediaApi implements AudioPlayerApi{

    private EMAudioPlayer mAudioPlayer;

    public AudioApi(EMAudioPlayer player) {
        mAudioPlayer = player;
        mAudioPlayer.setOnPreparedListener(this);
        mAudioPlayer.setOnCompletionListener(this);
        mAudioPlayer.setOnErrorListener(this);
        mAudioPlayer.setOnBufferUpdateListener(this);
        mAudioPlayer.setOnSeekCompletionListener(this);
    }

    @Override
    public boolean isPlaying() {
        return mAudioPlayer.isPlaying();
    }

    @Override
    public void play() {
        mAudioPlayer.start();
    }

    @Override
    public void pause() {
        mAudioPlayer.pause();
    }

    @Override
    public void stop() {
        mAudioPlayer.stopPlayback();
    }

    @Override
    public void reset() {
        mAudioPlayer.reset();
    }

    @Override
    public void release() {
        mAudioPlayer.release();
    }

    @Override
    public void setVolume(@FloatRange(from = 0.0, to = 1.0) float left, @FloatRange(from = 0.0, to = 1.0) float right) {
        mAudioPlayer.setVolume(left, right);
    }

    @Override
    public void seekTo(@IntRange(from = 0L) long milliseconds) {
        mAudioPlayer.seekTo((int) milliseconds);
    }

    @Override
    public long getCurrentPosition() {
        return mAudioPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mAudioPlayer.getDuration();
    }

    @Override
    public int getBufferedPercent() {
        return mAudioPlayer.getBufferPercentage();
    }


    @Override
    public void setStreamType(int streamType) {
        mAudioPlayer.setAudioStreamType(streamType);
    }

    @Override
    public void setWakeMode(Context context, int mode) {
        mAudioPlayer.setWakeMode(context, mode);
    }

    @Override
    public void setDataSource(@NonNull Context context, @NonNull Uri uri) {
        mAudioPlayer.setDataSource(context, uri);
    }

    @Override
    public void prepareAsync() {
        mAudioPlayer.prepareAsync();
    }


}
