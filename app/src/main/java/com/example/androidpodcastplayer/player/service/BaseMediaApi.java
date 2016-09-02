package com.example.androidpodcastplayer.player.service;


import android.support.annotation.IntRange;

import com.devbrackets.android.exomedia.listener.OnBufferUpdateListener;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.OnSeekCompletionListener;
import com.devbrackets.android.playlistcore.api.MediaPlayerApi;
import com.devbrackets.android.playlistcore.listener.OnMediaBufferUpdateListener;
import com.devbrackets.android.playlistcore.listener.OnMediaCompletionListener;
import com.devbrackets.android.playlistcore.listener.OnMediaErrorListener;
import com.devbrackets.android.playlistcore.listener.OnMediaPreparedListener;
import com.devbrackets.android.playlistcore.listener.OnMediaSeekCompletionListener;

public abstract class BaseMediaApi implements
        MediaPlayerApi,
        OnPreparedListener,
        OnCompletionListener,
        OnErrorListener,
        OnSeekCompletionListener,
        OnBufferUpdateListener{

    protected boolean mPrepared;
    protected int mBufferPercent;

    // define and register the appropriate listeners
    protected OnMediaPreparedListener mMediaPreparedListener;
    protected OnMediaCompletionListener mMediaCompletionListener;
    protected OnMediaSeekCompletionListener mMediaSeekCompletionListener;
    protected OnMediaErrorListener mMediaErrorListener;
    protected OnMediaBufferUpdateListener  mBufferUpdateListener;

    @Override
    public void setOnMediaPreparedListener(OnMediaPreparedListener listener) {
        mMediaPreparedListener = listener;
    }

    @Override
    public void setOnMediaCompletionListener(OnMediaCompletionListener listener) {
        mMediaCompletionListener = listener;
    }

    @Override
    public void setOnMediaSeekCompletionListener(OnMediaSeekCompletionListener listener) {
        mMediaSeekCompletionListener = listener;
    }

    @Override
    public void setOnMediaErrorListener(OnMediaErrorListener listener) {
        mMediaErrorListener = listener;
    }

    @Override
    public void setOnMediaBufferUpdateListener(OnMediaBufferUpdateListener listener) {
        mBufferUpdateListener = listener;
    }

    // forward exoplayer's listeners events
    @Override
    public void onPrepared() {
        mPrepared = true;
        if (mMediaPreparedListener != null) mMediaPreparedListener.onPrepared(this);
    }

    @Override
    public void onCompletion() {
        if (mMediaCompletionListener != null) mMediaCompletionListener.onCompletion(this);
    }

    @Override
    public boolean onError() {
        return mMediaErrorListener != null && mMediaErrorListener.onError(this);
    }

    @Override
    public void onSeekComplete() {
        if (mMediaSeekCompletionListener != null) mMediaSeekCompletionListener.onSeekComplete(this);
    }

    @Override
    public void onBufferingUpdate(@IntRange(from = 0L, to = 100L) int percent) {
        mBufferPercent = percent;
        if (mBufferUpdateListener != null) mBufferUpdateListener.onBufferingUpdate(this, percent);
    }

}
