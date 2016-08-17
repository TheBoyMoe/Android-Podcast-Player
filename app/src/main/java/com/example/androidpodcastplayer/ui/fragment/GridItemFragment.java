package com.example.androidpodcastplayer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.custom.ItemSpacerDecoration;
import com.example.androidpodcastplayer.custom.AutofitRecyclerView;
import com.example.androidpodcastplayer.model.ItunesGenre;
import com.example.androidpodcastplayer.model.ItunesGenreDataCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridItemFragment extends ContractFragment<GridItemFragment.Contract>{

    public interface Contract {
        void gridItemClick(int genreId);
    }

    public GridItemFragment() {}

    public static GridItemFragment newInstance() {
        return new GridItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_grid_recycler, container, false);
        AutofitRecyclerView recyclerView = (AutofitRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new ItemSpacerDecoration(
                getResources().getDimensionPixelOffset(R.dimen.grid_item_margin),
                getResources().getDimensionPixelOffset(R.dimen.grid_item_margin)
        ));
        recyclerView.setHasFixedSize(true);
        //List<Integer> list = new ArrayList<>(Arrays.asList(mGridIcons));
        List<ItunesGenre> list = new ArrayList<>(Arrays.asList(ItunesGenreDataCache.list));
        GridItemAdapter adapter = new GridItemAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.ViewHolder>{

        private List<ItunesGenre> mList;
        private Context mContext;

        GridItemAdapter(List<ItunesGenre> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindModelItem(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView mIcon;
            TextView mTitle;
            int mId;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mIcon = (ImageView) itemView.findViewById(R.id.item_icon);
                mTitle = (TextView) itemView.findViewById(R.id.item_title);
            }

            public void bindModelItem(ItunesGenre item) {
                mId = item.getGenreId();
                mTitle.setText(item.getTitle());
                Utils.loadPreviewWithGlide(mContext, item.getDrawable(), mIcon);
            }

            @Override
            public void onClick(View view) {
                // forward click event to hosting fragment
                getContract().gridItemClick(mId);
            }
        }


    }



}
