package com.example.androidpodcastplayer.player.manager;

import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

import com.devbrackets.android.playlistcore.manager.ListPlaylistManager;
import com.example.androidpodcastplayer.PodcastPlayerApplication;
import com.example.androidpodcastplayer.player.model.AudioItem;
import com.example.androidpodcastplayer.player.service.AudioService;


public class PlaylistManager extends ListPlaylistManager<AudioItem>{

    @NonNull
    @Override
    protected Application getApplication() {
        return PodcastPlayerApplication.getsApplication();
    }

    @NonNull
    @Override
    protected Class<? extends Service> getMediaServiceClass() {
        return AudioService.class;
    }


}
