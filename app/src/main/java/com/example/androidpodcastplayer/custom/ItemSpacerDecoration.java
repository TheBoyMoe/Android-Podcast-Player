package com.example.androidpodcastplayer.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemSpacerDecoration extends RecyclerView.ItemDecoration{

    private int mVertical;
    private int mHorizontal;

    public ItemSpacerDecoration(int vertical, int horizontal) {
        mVertical = vertical;
        mHorizontal = horizontal;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = mHorizontal;
        outRect.right = mHorizontal;
        outRect.bottom = mVertical;
        outRect.top = mVertical;
    }

}
