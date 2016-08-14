package com.example.androidpodcastplayer.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public class ContractFragment<T> extends Fragment {

    private T mContract;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mContract = (T) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    activity.getClass().getSimpleName()
                            + " does not implement contract interface for "
                            + getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContract = null;
    }

    public final T getContract() {
        return mContract;
    }


}
