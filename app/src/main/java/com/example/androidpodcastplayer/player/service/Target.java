package com.example.androidpodcastplayer.player.service;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface Target {

    void onBitmapLoaded(Bitmap bitmap);
    void onBitmapFailed(Drawable errorDrawable);
    void onPrepareLoad(Drawable placeholderDrawable);

}
