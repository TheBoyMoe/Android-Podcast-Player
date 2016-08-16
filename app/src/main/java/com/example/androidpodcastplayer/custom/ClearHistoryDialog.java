package com.example.androidpodcastplayer.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;

import timber.log.Timber;


public class ClearHistoryDialog extends DialogFragment implements View.OnClickListener{

    // callback implemented by the activity
    public interface CallbackListener {
        void confirmHistoryCleared(boolean historyCleared);
    }

    private CallbackListener mListener;
    private boolean mHistoryCleared = false;

    public ClearHistoryDialog() {}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_clear_history, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        (view.findViewById(R.id.dialog_positive_btn)).setOnClickListener(this);
        (view.findViewById(R.id.dialog_negative_btn)).setOnClickListener(this); // needs to be enabled to be dismissed
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (CallbackListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_positive_btn:
                mHistoryCleared = true;
                break;
        }
        // inform the activity the history has been cleared
        mListener.confirmHistoryCleared(mHistoryCleared);
        dismiss();
    }


}

