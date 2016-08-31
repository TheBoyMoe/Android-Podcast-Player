package com.example.androidpodcastplayer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.custom.AutofitRecyclerView;
import com.example.androidpodcastplayer.custom.ItemSpacerDecoration;
import com.example.androidpodcastplayer.model.episode.Channel;
import com.example.androidpodcastplayer.model.episode.Image;
import com.example.androidpodcastplayer.model.episode.Item;
import com.example.androidpodcastplayer.model.podcast.Podcast;

import java.util.List;
import java.util.Locale;

/**
 * References:
 * [1] http://stackoverflow.com/questions/31662416/show-collapsingtoolbarlayout-title-only-when-collapsed
 * [2] http://stackoverflow.com/questions/240546/remove-html-tags-from-a-string
 */

public class EpisodesFragment extends ContractFragment<EpisodesFragment.Contract>{

    public interface Contract {
        void launchPlayer(Item episode, String imageUrl);
        void addEpisodeToPlaylist();
        void downloadEpisode();
        void downloadError(String message);
        void onNavigationIconBackPressed();
    }

    private LinearLayout mPodcastInfo;
    private CoordinatorLayout mLayout;
    private String mPodcastName;
    private AutofitRecyclerView mRecyclerView;
    private TextView mPodcastTitle;
    private TextView mPodcastAuthor;
    private TextView mPodcastDescription;
    private TextView mPodcastGenre;
    private ImageView mPodcastThumbnail;
    private String mImageUrl;

    public EpisodesFragment() {}

    public static EpisodesFragment newInstance(Podcast item, Channel channel) {
        EpisodesFragment fragment = new EpisodesFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PODCAST_ITEM, item);
        args.putParcelable(Constants.PODCAST_CHANNEL, channel);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_episodes, container, false);
        initToolbar(view);
        initFab(view);
        setupInfoView(view);
        setupListView(view);

        Podcast podcast = getArguments().getParcelable(Constants.PODCAST_ITEM);
        Channel channel = getArguments().getParcelable(Constants.PODCAST_CHANNEL);
        if (podcast != null && channel != null) {
            displayContent(podcast, channel);
            setupAppBar(view);
        }

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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);
            actionBar.setTitle("");
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

    private void setupInfoView(View view) {
        mPodcastInfo = (LinearLayout) view.findViewById(R.id.podcast_info);
        mPodcastTitle = (TextView) view.findViewById(R.id.podcast_title);
        mPodcastAuthor = (TextView) view.findViewById(R.id.podcast_author);
        mPodcastDescription = (TextView) view.findViewById(R.id.podcast_description);
        mPodcastGenre = (TextView) view.findViewById(R.id.podcast_genre);
        mPodcastThumbnail = (ImageView) view.findViewById(R.id.podcast_thumbnail);
    }

    private void setupListView(View view) {
        mLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        RelativeLayout listContainer = (RelativeLayout) view.findViewById(R.id.autofitrecycler_container);
        listContainer.setPadding(0, 0, 0, 0); // remove top padding
        mRecyclerView = (AutofitRecyclerView) view.findViewById(R.id.recycler_view);
        // mRecyclerView.setHasFixedSize(true); // height varies
        mRecyclerView.addItemDecoration(new ItemSpacerDecoration(
                getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_margin),
                getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_margin)
        ));
    }

    private void displayContent(Podcast podcast, Channel channel) {

        // page and podcast title
        if (channel.getTitle()!= null && !channel.getTitle().isEmpty()) {
            mPodcastName = Utils.htmlToStringParser(channel.getTitle());
        } else {
            mPodcastName = podcast.getTrackName() != null ? Utils.htmlToStringParser(podcast.getTrackName()) : " ";
        }
        mPodcastTitle.setText(mPodcastName);

        // author
        if (channel.getAuthor() != null && !channel.getAuthor().isEmpty()) {
            mPodcastAuthor.setText(Utils.htmlToStringParser(channel.getAuthor()));
        } else {
            mPodcastAuthor.setText(podcast.getArtistName() != null ? Utils.htmlToStringParser(podcast.getArtistName()) : "");
        }

        // description
        if (channel.getDescription() != null && !channel.getDescription().isEmpty()) {
            mPodcastDescription.setText(Utils.htmlToStringParser(channel.getDescription()));
        } else {
            mPodcastDescription.setText(podcast.getCollectionName() != null ? Utils.htmlToStringParser(podcast.getCollectionName()) : "");
        }

        // genre
        if (podcast.getPrimaryGenreName() != null && !podcast.getPrimaryGenreName().isEmpty()) {
            mPodcastGenre.setText(String.format("Genre : %s", podcast.getPrimaryGenreName()));
        }

        // use glide to download and display thumbnail
        if (podcast.getArtworkUrl600() != null && !podcast.getArtworkUrl600().isEmpty()) {
            Utils.loadPreviewWithGlide(getActivity(), podcast.getArtworkUrl600(), mPodcastThumbnail);
        } else {
            Utils.loadPreviewWithGlide(getActivity(), R.drawable.no_image_600x600, mPodcastThumbnail);
        }

        // full size image
        List<Image> images = channel.getImages();
        if (images != null) {
            for (Image image : images) {
                mImageUrl = image.getUrl();
                if (mImageUrl != null) break;
            }
        }

        // instantiate and bind adapter
        List<Item> episodes = channel.getItemList();
        if (episodes != null && episodes.size() > 0) {
            EpisodeListAdapter adapter = new EpisodeListAdapter(episodes);
            mRecyclerView.setAdapter(adapter);
        }

    }

    // display the page title when toolbar collapses
    private void setupAppBar(View view) {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        final AppBarLayout appbar = (AppBarLayout) view.findViewById(R.id.app_bar);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appbar.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(mPodcastName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.episode_item, parent, false);
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

            Item mEpisode;
            TextView mEpisodeDay;
            TextView mEpisodeDescription;
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
                mEpisodeDescription = (TextView) itemView.findViewById(R.id.episode_description);
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
                if (episode != null) {
                    String day = " ";
                    String month = " ";
                    mEpisode = episode;
                    if (mEpisode.getPubDate() != null && !mEpisode.getPubDate().isEmpty()) {
                        // Timber.i("%s: date: %s",Constants.LOG_TAG, mEpisode.getPubDate());
                        day = mEpisode.getPubDate().substring(5, 7);
                        int length = day.length(); // two
                        String temp = day.trim(); // any spaces are removed
                        if (temp.length() < length) {
                            day = temp;
                            month = mEpisode.getPubDate().substring(7, 10);
                        } else {
                            month = mEpisode.getPubDate().substring(8, 11);
                        }
                    }
                    mEpisodeDay.setText(day);
                    mEpisodeMonth.setText(month);

                    // set title
                    if (mEpisode.getTitle() != null && !mEpisode.getTitle().isEmpty()) {
                        mEpisodeTitle.setText(Utils.htmlToStringParser(mEpisode.getTitle()));
                    } else {
                        mEpisodeTitle.setText(mEpisode.getAuthor() != null ? Utils.htmlToStringParser(mEpisode.getAuthor()) : "");
                    }


                    // set description
                    if (mEpisode.getSubtitle() != null && !mEpisode.getSubtitle().isEmpty()) {
                        mEpisodeDescription.setText(Utils.htmlToStringParser(mEpisode.getSubtitle()));
                    } else if (mEpisode.getDescription() != null && !mEpisode.getDescription().isEmpty()){
                        mEpisodeDescription.setText(Utils.htmlToStringParser(mEpisode.getDescription()));
                    } else {
                        mEpisodeDescription.setText(mEpisode.getAuthor() != null ? Utils.htmlToStringParser(mEpisode.getAuthor()) : "");
                    }

                    // set episode duration // FIXME
                    if (episode.getDuration() != null && !episode.getDuration().isEmpty())
                        mEpisodeDuration.setText(String.format(Locale.ENGLISH, "%s mins", episode.getDuration()));
                }
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.episode_item:
                    case R.id.episode_play:
                        getContract().launchPlayer(mEpisode, mImageUrl);
                        break;
                    case R.id.episode_download:
                        // TODO
                        Utils.showSnackbar(mLayout, "Clicked download");
                        break;
                    case R.id.episode_playlist:
                        // TODO
                        Utils.showSnackbar(mLayout, "Clicked playlist");
                        break;
                }
            }
        }


    }


    private void centerProgressBar() {

        // calculate height of display in dp
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        final float density = getActivity().getResources().getDisplayMetrics().density;
        float dpHeight = metrics.heightPixels/density;

        ViewTreeObserver infoViewObserver = mPodcastInfo.getViewTreeObserver();
        if (infoViewObserver.isAlive()) {

            final float finalDpDisplayHeight = dpHeight;
            final float actionBarHeight = 56f;

            infoViewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mPodcastInfo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    float dpInfoHeight = mPodcastInfo.getHeight()/density;
                    //mProgressBar.setY(finalDpDisplayHeight - (dpInfoHeight + actionBarHeight));
                    // mProgressBar.setY(finalDpDisplayHeight);
                }
            });
        }

    }


}
