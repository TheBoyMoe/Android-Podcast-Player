package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;


public class BaseFragment extends Fragment{

    protected TextView mContent;

    public BaseFragment() {}

    public static BaseFragment newInstance() {
        return new BaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        mContent = (TextView) view.findViewById(R.id.content_text);
        return view;
    }


}


