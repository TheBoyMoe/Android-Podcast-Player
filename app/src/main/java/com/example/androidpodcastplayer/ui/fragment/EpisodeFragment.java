package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EpisodeFragment extends ContractFragment<EpisodeFragment.Contract>{

    public interface Contract {
        void onItemClick(String episodeUrl);
    }

    public EpisodeFragment() {}

    public static EpisodeFragment newInstance() {
        return new EpisodeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }


}
