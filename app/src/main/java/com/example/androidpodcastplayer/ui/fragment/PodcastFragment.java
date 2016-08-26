package com.example.androidpodcastplayer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.custom.AutofitRecyclerView;
import com.example.androidpodcastplayer.custom.ItemSpacerDecoration;
import com.example.androidpodcastplayer.model.podcast.Podcast;
import com.example.androidpodcastplayer.model.podcast.Results;
import com.example.androidpodcastplayer.rest.ApiClient;
import com.example.androidpodcastplayer.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class PodcastFragment extends ContractFragment<PodcastFragment.Contract>{

    public interface Contract {
        // void onItemClick(String feedUrl);
        void onItemClick(Podcast podcast);
        void downloadError(String message);
    }

    private PodcastListAdapter mAdapter;
    private AutofitRecyclerView mRecyclerView;
    private TextView mEmptyView;
    private List<Podcast> mPodcastList = new ArrayList<>();
    private ProgressBar mProgressBar;

    public PodcastFragment() {}

    public static PodcastFragment newInstance(int genreId) {
        PodcastFragment fragment = new PodcastFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.GENRE_ID, genreId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_auto_recycler_list, container, false);
        setupView(view);
        // provide an empty list allowing the adapter to be attached
        mAdapter = new PodcastListAdapter(mPodcastList);
        mRecyclerView.setAdapter(mAdapter);
        // download data and bind to the adapter
        displayContent();

        return view;
    }

    private void setupView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mRecyclerView = (AutofitRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemSpacerDecoration(
                getResources().getDimensionPixelOffset(R.dimen.grid_item_margin),
                getResources().getDimensionPixelOffset(R.dimen.grid_item_margin)
        ));
    }

    private void displayContent() {
        int genreId = getArguments().getInt(Constants.GENRE_ID);
        if (genreId > 0) {
            executeGenreQuery(genreId);
        } else {
            getContract().downloadError("Error downloading podcasts");
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void executeGenreQuery(int genreId) {
        // instantiate retrofit client and execute rest call asynchronously
        Timber.i("%s: executing podcast download task", Constants.LOG_TAG);
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface restService = ApiClient.getClient().create(ApiInterface.class);
        Call<Results> call = restService.getGenrePodcasts(
                Constants.REST_TERM, genreId, Constants.REST_LIMIT
        );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                mProgressBar.setVisibility(View.GONE);
                mPodcastList = response.body().getResults();
                if (mPodcastList != null && mPodcastList.size() > 0) {
                    mAdapter = new PodcastListAdapter(mPodcastList);
                    mRecyclerView.swapAdapter(mAdapter, true);
                    mEmptyView.setVisibility(View.GONE);
                } else {
                    getContract().downloadError("Error downloading podcasts");
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Timber.e("%s Retrofit error: %s", Constants.LOG_TAG, t.getMessage());
                getContract().downloadError(t.getMessage());
                mEmptyView.setText(R.string.error_connecting_text);
                mEmptyView.setVisibility(View.VISIBLE);
            }
        });
    }


    class PodcastListAdapter extends RecyclerView.Adapter<PodcastListAdapter.ViewHolder>{

        private List<Podcast> mList;
        private Context mContext;

        PodcastListAdapter(List<Podcast> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.podcast_item, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindModelItem(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return (mList != null) ? mList.size() : 0;
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            Podcast podcast;
            String mFeedUrl;
            ImageView mThumbnail;
            TextView mArtistName;
            TextView mCollectionName;
            TextView mNumberOfEpisodes;
            TextView mLatestPublicationDate;


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mThumbnail = (ImageView) itemView.findViewById(R.id.podcast_thumbnail);
                mArtistName = (TextView) itemView.findViewById(R.id.podcast_artist_name);
                mCollectionName = (TextView) itemView.findViewById(R.id.podcast_collection_name);
                mNumberOfEpisodes = (TextView) itemView.findViewById(R.id.podcast_episode_number);
                mLatestPublicationDate = (TextView) itemView.findViewById(R.id.podcst_latest_pubdate);
            }

            public void bindModelItem(Podcast item) {
                podcast = item;
                mFeedUrl = item.getFeedUrl();
                mArtistName.setText(item.getArtistName());
                mCollectionName.setText(item.getCollectionName());
                mNumberOfEpisodes.setText(String.valueOf(item.getTrackCount()));
                mLatestPublicationDate.setText(item.getReleaseDate());
                // use glide to download and display image
                Utils.loadPreviewWithGlide(mContext, item.getArtworkUrl100(), mThumbnail);
            }

            @Override
            public void onClick(View view) {
                // forward the podcast rss feed to the hosting activity
                getContract().onItemClick(podcast);
            }
        }

    }


}
