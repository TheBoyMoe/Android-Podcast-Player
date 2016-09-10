package com.example.androidpodcastplayer.ui.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.custom.QuerySuggestionProvider;
import com.example.androidpodcastplayer.model.podcast.Podcast;
import com.example.androidpodcastplayer.model.podcast.Results;
import com.example.androidpodcastplayer.rest.ApiClient;
import com.example.androidpodcastplayer.rest.ApiInterface;
import com.example.androidpodcastplayer.ui.fragment.GenreItemFragment;
import com.example.androidpodcastplayer.ui.fragment.ListItemFragment;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements
        GenreItemFragment.Contract,
        ListItemFragment.Contract {


    // implementation of interface methods
    @Override
    public void listItemClick(int position) {
        Utils.showSnackbar(mLayout, "Clicked list item " + position);
    }

    @Override
    public void genreItemClick(int genreId, String genreTitle) {
        // launch PodcastActivity which will execute download of podcasts
        // for the relevant genre and display the results
        if (Utils.isClientConnected(this)) {
            // PodcastActivity.launch(this, genreId, genreTitle);
            executeGenreQuery(genreId, genreTitle);
        } else {
            Utils.showSnackbar(mLayout, getString(R.string.no_network_connection));
        }
    }
    // END


    private SearchRecentSuggestions mRecentSuggestions;
    private MenuItem mSearchItem;
    private SearchView mSearchView;
    private CoordinatorLayout mLayout;
    private ProgressBar mProgressBar;
    private TabLayout mTabLayout;
    private int[] mTabIcons = {
            R.drawable.ic_explore,
            R.drawable.ic_subscription,
            R.drawable.ic_playlist
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setY(112f); // center progressbar, move down due to TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        // instantiate the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate the ViewPager, fragments & icons
        setupViewPager(viewPager);
        mTabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        // ensures there is a ref to suggestions on startup/device rotation
        mRecentSuggestions = new SearchRecentSuggestions(
                MainActivity.this,
                QuerySuggestionProvider.AUTHORITY,
                QuerySuggestionProvider.MODE
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable config with SearchView widget
        SearchManager search = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setSearchableInfo(search.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        mSearchView.setOnQueryTextListener(listener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (mRecentSuggestions != null) {
                    ClearHistoryDialog dialog = new ClearHistoryDialog();
                    dialog.show(getSupportFragmentManager(), "clear history");
                } else {
                    Utils.showSnackbar(mLayout, "History clear");
                }
                return true;
            case R.id.action_settings:
                Utils.showSnackbar(mLayout, "Clicked on settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // re-create ref in case the suggestions has been cleared since startup
            mRecentSuggestions = new SearchRecentSuggestions(
                    MainActivity.this,
                    QuerySuggestionProvider.AUTHORITY,
                    QuerySuggestionProvider.MODE);
            // save queries to suggestions provider
            mRecentSuggestions.saveRecentQuery(query, null);

            // if we're connected, execute the search
            if (Utils.isClientConnected(MainActivity.this)) {
                executeSearchQuery(query);
            } else {
                Utils.showSnackbar(mLayout, getString(R.string.no_network_connection));
            }

            // hide the soft keyboard & close the search view
            Utils.hideKeyboard(MainActivity.this, mSearchView.getWindowToken());
            mSearchItem.collapseActionView();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // no-op
            return false;
        }
    };

    // search iTunes for podcasts matching the search query
    private void executeSearchQuery(final String query) {
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface searchService = ApiClient.getClient().create(ApiInterface.class);
        Call<Results> call = searchService.getGenrePodcasts(
                query, Constants.PODCAST_ID, Constants.REST_LIMIT, Constants.REST_SORT_RECENT
        );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                mProgressBar.setVisibility(View.GONE);
                ArrayList<Podcast> results = (ArrayList<Podcast>) response.body().getResults();
                if (results != null && results.size() > 0) {
                    // display results in PodcastActivity/Fragment
                    PodcastActivity.launch(MainActivity.this, results, query, true);
                } else {
                    Utils.showSnackbar(mLayout, "No results returned");
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Utils.showSnackbar(mLayout, "Server error, try again");
                Timber.e("%s error executing search query: %s", Constants.LOG_TAG, t.getMessage());
            }
        });
    }

    // return a list of podcasts for the supplied genre
    private void executeGenreQuery(int genreId, final String genreTitle) {
        mProgressBar.setVisibility(View.VISIBLE);
        Timber.i("%s: executing podcast download task", Constants.LOG_TAG);
        ApiInterface restService = ApiClient.getClient().create(ApiInterface.class);
        Call<Results> call = restService.getGenrePodcasts(
                Constants.REST_TERM, genreId, Constants.REST_LIMIT, Constants.REST_SORT_POPULAR
        );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                mProgressBar.setVisibility(View.GONE);
                ArrayList<Podcast> list = (ArrayList<Podcast>) response.body().getResults();
                if (list != null && list.size() > 0) {
                    PodcastActivity.launch(MainActivity.this, list, genreTitle, false);
                } else {
                    Utils.showSnackbar(mLayout, "No results found");
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Utils.showSnackbar(mLayout, "Server error, try again");
                Timber.e("%s error executing genre query: %s", Constants.LOG_TAG, t.getMessage());
            }
        });
    }

    public void confirmHistoryCleared(boolean historyCleared) {
        if (historyCleared) {
            mRecentSuggestions.clearHistory();
            mRecentSuggestions = null;
            Utils.showSnackbar(mLayout, "History successfully cleared");
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(mTabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(mTabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(mTabIcons[2]);
        //mTabLayout.getTabAt(3).setIcon(mTabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GenreItemFragment.newInstance(), "Explore");
        adapter.addFragment(ListItemFragment.newInstance(), "Subscription");
        adapter.addFragment(ListItemFragment.newInstance(), "Playlist");
        // adapter.addFragment(SubscriptionFragment.newInstance(), "Subscription");
        // adapter.addFragment(PlaylistFragment.newInstance(), "Playlist");
        viewPager.setAdapter(adapter);
    }


    private class CustomViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mTitleList = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null; // icon only tab
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mTitleList.add(title);
        }

    }


    public static class ClearHistoryDialog extends DialogFragment implements View.OnClickListener {

        private boolean mHistoryCleared = false;

        public ClearHistoryDialog() {
        }

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
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.dialog_positive_btn:
                    mHistoryCleared = true;
                    break;
            }
            ((MainActivity) getActivity()).confirmHistoryCleared(mHistoryCleared);
            dismiss();
        }

    }


}
