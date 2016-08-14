package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.custom.ItemSpacerDecoration;
import com.example.androidpodcastplayer.custom.AutofitRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridItemFragment extends ContractFragment<GridItemFragment.Contract>{

    public interface Contract {
        void gridItemClick(int position);
    }

    private Integer[] mGridIcons = {
            R.drawable.ic_mic,
            R.drawable.ic_person,
            R.drawable.ic_international,
            R.drawable.ic_talk,
            R.drawable.ic_queue,
            R.drawable.ic_mic,
            R.drawable.ic_person,
            R.drawable.ic_international,
            R.drawable.ic_talk,
            R.drawable.ic_queue,
            R.drawable.ic_mic,
            R.drawable.ic_person,
            R.drawable.ic_international,
            R.drawable.ic_talk,
            R.drawable.ic_queue,
            R.drawable.ic_mic,
            R.drawable.ic_person,
            R.drawable.ic_international,
            R.drawable.ic_talk,
            R.drawable.ic_queue
    };

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
        List<Integer> list = new ArrayList<>(Arrays.asList(mGridIcons));
        GridItemAdapter adapter = new GridItemAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.ViewHolder>{

        private List<Integer> mList;

        public GridItemAdapter(List<Integer> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
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

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            }

            public void bindModelItem(Integer icon) {
                mIcon.setImageResource(icon);
            }

            @Override
            public void onClick(View view) {
                // forward click event to hosting fragment
                getContract().gridItemClick(getAdapterPosition());
            }
        }


    }



}
