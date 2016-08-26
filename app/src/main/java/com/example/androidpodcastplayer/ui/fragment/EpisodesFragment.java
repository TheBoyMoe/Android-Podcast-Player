package com.example.androidpodcastplayer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.androidpodcastplayer.model.episode.Category;
import com.example.androidpodcastplayer.model.episode.Channel;
import com.example.androidpodcastplayer.model.episode.Feed;
import com.example.androidpodcastplayer.model.episode.Image;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.rest.RssClient;
import com.example.androidpodcastplayer.rest.RssInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class EpisodesFragment extends ContractFragment<EpisodesFragment.Contract>{

    public interface Contract {
        void onItemClick(String podcastName);
        void downloadError(String message);
        void onNavigationIconBackPressed();
    }

    private CoordinatorLayout mLayout;
    private String mPodcastName;
    private AutofitRecyclerView mRecyclerView;
    private TextView mEmptyView;
    private ProgressBar mProgressBar;
    private EpisodeListAdapter mAdapter;
    private View mWrapper;

    public EpisodesFragment() {}

    public static EpisodesFragment newInstance(String feedUrl) {
        EpisodesFragment fragment = new EpisodesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.RSS_FEED_URL, feedUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_episodes, container, false);
        setupListView(view);
        initToolbar(view);
        initFab(view);

        // bind the adapter to the view
        mAdapter = new EpisodeListAdapter(new ArrayList<Item>());
        mRecyclerView.setAdapter(mAdapter);

        displayContent();

        return view;
    }


    // helper methods
    private void initToolbar(View view) {
        // instantiate the toolbar with up nav arrow
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getContract().onNavigationIconBackPressed();
                }
            });
        }
    }

    private void initFab(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPodcastName != null) {
                    // TODO save the podcast to the playlist
                    // getContract().onItemClick(mPodcastName);
                    Utils.showSnackbar(mLayout, mPodcastName);
                }
            }
        });
    }

    private void setupListView(View view) {
        mLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mWrapper = view.findViewById(R.id.autofitrecycler_container);
        mWrapper.setPadding(0, 0, 0, 0);
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
        String urlFeed = getArguments().getString(Constants.RSS_FEED_URL);
        if (urlFeed != null) {
            Timber.i("%s feed: %s", Constants.LOG_TAG, urlFeed);
            executeEpisodeQuery(urlFeed);
        } else {
            mEmptyView.setText("Error, downloading episode list");
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    // download the podcast episode list
    private void executeEpisodeQuery(String feedUrl) {
        Timber.i("%s execute episode list download", Constants.LOG_TAG);
        mProgressBar.setVisibility(View.VISIBLE);
        RssInterface rssService = RssClient.getClient().create(RssInterface.class);
        Call<Feed> call = rssService.getItems(feedUrl);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Channel channel = response.body().getChannel();
                mPodcastName = channel.getTitle();
                List<Image> images = channel.getImages();
                String url = null;
                for (Image image : images) {
                    url = image.getUrl();
                    if (url != null) break;
                }
                String cat = null;
                List<Category> categories = channel.getCategory();
                for (Category category : categories) {
                    cat = category.getCategory();
                    if (cat != null) break;
                }
                Timber.i("%s title: %s, pubDate: %s, buildDate: %s, language: %s, author: %s, description: %s, image link %s, category %s, episodes %s",
                        Constants.LOG_TAG, channel.getTitle(), channel.getPubDate(), channel.getBuildDate(), channel.getLanguage(),
                        channel.getAuthor(), channel.getDescription(), url, cat, channel.getItemList().size());

                // episode list
                mProgressBar.setVisibility(View.GONE);
                List<Item> episodes = channel.getItemList();
                if (episodes != null && episodes.size() > 0) {
                    mAdapter = new EpisodeListAdapter(episodes);
                    mRecyclerView.swapAdapter(mAdapter, true);
                    mEmptyView.setVisibility(View.GONE);
                } else {
                    Utils.showSnackbar(mLayout, "Error downloading episodes");
                    mEmptyView.setText("Error downloading episode list");
                    mEmptyView.setVisibility(View.VISIBLE);
                }

                // DEBUG
//                if (episodes != null && episodes.size() > 0) {
//                    int count = 5;
//                    if (episodes.size() < count) {
//                        count = episodes.size();
//                    }
//                    for (int i = 0; i < episodes.size(); i++) {
//                        Item episode = episodes.get(i);
//                        String imageUrl = null;
//                        if (episode.getImage() != null) {
//                            imageUrl = episode.getImage().getUrl();
//                        }
//
//                        Timber.i("%s title: %s, pubDate: %s, duration: %s, description: %s, " +
//                                        "author: %s, imageUrl: %s, episodeLength: %s, episodeType: %s, episodeUrl: %s",
//                                Constants.LOG_TAG, episode.getTitle(), episode.getPubDate(),
//                                episode.getDuration(), episode.getDescription(), episode.getAuthor(),
//                                imageUrl, episode.getEpisodeInfo().getLength(),
//                                episode.getEpisodeInfo().getType(), episode.getEpisodeInfo().getUrl());
//                    }
//                }
                // END
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Timber.e("%s failure, error: %s", Constants.LOG_TAG, t.getMessage());
                // getContract().downloadError("Error downloading podcast feed");
                mEmptyView.setText(R.string.error_connecting_text);
                mEmptyView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }

        });
    }


    class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>{

        private List<Item> mEpisodeList;
        private Context mContext;

        public EpisodeListAdapter(List<Item> list) {
            mEpisodeList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_episode, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindModelItem(mEpisodeList.get(position));
        }

        @Override
        public int getItemCount() {
            return mEpisodeList != null ? mEpisodeList.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView mEpisodeDay;
            TextView mEpisodeNumber;
            TextView mEpisodeMonth;
            TextView mEpisodeTitle;
            ImageView mEpisodePlay;
            TextView mEpisodeDuration;
            ImageView mEpisodeDownload;
            ImageView mEpisodePlaylist;
            View mEpisodeItem;

            public ViewHolder(View itemView) {
                super(itemView);
                mEpisodeItem = itemView.findViewById(R.id.episode_item);
                mEpisodeDay = (TextView) itemView.findViewById(R.id.episode_day);
                mEpisodeNumber = (TextView) itemView.findViewById(R.id.episode_number);
                mEpisodeMonth = (TextView) itemView.findViewById(R.id.episode_month);
                mEpisodeTitle = (TextView) itemView.findViewById(R.id.episode_title);
                mEpisodeDuration = (TextView) itemView.findViewById(R.id.episode_duration);
                mEpisodePlay = (ImageView) itemView.findViewById(R.id.episode_play);
                mEpisodeDownload = (ImageView) itemView.findViewById(R.id.episode_download);
                mEpisodePlaylist = (ImageView) itemView.findViewById(R.id.episode_playlist);
                mEpisodeItem.setOnClickListener(this);
                mEpisodePlay.setOnClickListener(this);
                mEpisodeDownload.setOnClickListener(this);
                mEpisodePlaylist.setOnClickListener(this);
            }

            public void bindModelItem(Item episode) {
                mEpisodeDay.setText("26");
                mEpisodeNumber.setText(String.format(Locale.ENGLISH, "Episode no: %d", (mEpisodeList.size() - getAdapterPosition())));
                mEpisodeMonth.setText("Aug");
                mEpisodeTitle.setText(episode.getTitle());
                mEpisodeDuration.setText(String.format(Locale.ENGLISH, "%s mins", episode.getDuration()));
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.episode_item:
                        Utils.showSnackbar(mLayout, "clicked item");
                        break;
                    case R.id.episode_play:
                        Utils.showSnackbar(mLayout, "Clicked play");
                        break;
                    case R.id.episode_download:
                        Utils.showSnackbar(mLayout, "Clicked download");
                        break;
                    case R.id.episode_playlist:
                        Utils.showSnackbar(mLayout, "Clicked playlist");
                        break;
                }
            }
        }


    }

}
