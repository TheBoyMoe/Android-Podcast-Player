package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.custom.ItemSpacerDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListItemFragment extends ContractFragment<ListItemFragment.Contract>{

    public interface Contract {
        void listItemClick(int position);
    }

    public ListItemFragment() {}

    public static ListItemFragment newInstance(){
        return new ListItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_recycler_std, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemSpacerDecoration(
                getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_margin),
                getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_margin)
        ));
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("#item " + (i + 1));
        }
        ListItemAdapter adapter = new ListItemAdapter(items);
        recyclerView.setAdapter(adapter);
        return view;
    }



    class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder>{

        private List<String> mList;

        public ListItemAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(itemView);
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

            TextView mTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mTitle = (TextView) itemView.findViewById(R.id.item_title);
            }

            public void bindModelItem(String title) {
                mTitle.setText(title);
            }

            @Override
            public void onClick(View view) {
                // forward click event to hosting activity
                getContract().listItemClick(getAdapterPosition() + 1);
            }
        }


    }

}
