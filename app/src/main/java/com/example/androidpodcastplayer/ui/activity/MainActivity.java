package com.example.androidpodcastplayer.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.custom.ClearHistoryDialog;
import com.example.androidpodcastplayer.custom.QuerySuggestionProvider;
import com.example.androidpodcastplayer.ui.fragment.GridItemFragment;
import com.example.androidpodcastplayer.ui.fragment.ListItemFragment;
import com.example.androidpodcastplayer.ui.fragment.PlaylistFragment;
import com.example.androidpodcastplayer.ui.fragment.SubscriptionFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GridItemFragment.Contract,
        ListItemFragment.Contract,
        ClearHistoryDialog.CallbackListener{

    // implementation of interface methods
    @Override
    public void listItemClick(int position) {
        Utils.showSnackbar(mLayout, "Clicked list item " + position);
    }

    @Override
    public void gridItemClick(int position) {
        Utils.showSnackbar(mLayout, "Clicked grid item " + position);
    }

    @Override
    public void confirmHistoryCleared(boolean historyCleared) {
        if (historyCleared) {
            mRecentSuggestions.clearHistory();
            mRecentSuggestions = null;
            Utils.showSnackbar(mLayout, "History successfully cleared");
        }
    }
    // END


    private SearchRecentSuggestions mRecentSuggestions;
    private MenuItem mSearchItem;
    private SearchView mSearchView;
    private CoordinatorLayout mLayout;
    private TabLayout mTabLayout;
    private int[] mTabIcons = {
            R.drawable.ic_explore,
            R.drawable.ic_top_fifty,
            R.drawable.ic_subscription,
            R.drawable.ic_playlist
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // instantiate the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate the ViewPager, fragments & icons
        setupViewPager(viewPager);
        mTabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable config with SearchView widget
        SearchManager search = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchItem = menu.findItem(R.id.action_search);
        //  mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
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
                    // FIXME suggestions ref lost on device rotation
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
            // save queries to suggestions provider
            mRecentSuggestions = new SearchRecentSuggestions(
                    MainActivity.this,
                    QuerySuggestionProvider.AUTHORITY,
                    QuerySuggestionProvider.MODE);
            mRecentSuggestions.saveRecentQuery(query, null);
            executeSearchQuery(query);

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

    private void executeSearchQuery(String query) {
        // TODO search iTunes
        Utils.showSnackbar(mLayout, "Execute search: " + query);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(mTabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(mTabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(mTabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(mTabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GridItemFragment.newInstance(), "Explore");
        adapter.addFragment(ListItemFragment.newInstance(), "Categories");
        adapter.addFragment(SubscriptionFragment.newInstance(), "Subscription");
        adapter.addFragment(PlaylistFragment.newInstance(), "Playlist");
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


//    public static class ClearHistoryDialog extends DialogFragment implements View.OnClickListener{
//
//        public ClearHistoryDialog() {}
//
//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.dialog_clear_history, container, false);
//            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            (view.findViewById(R.id.dialog_positive_btn)).setOnClickListener(this);
//            (view.findViewById(R.id.dialog_negative_btn)).setOnClickListener(this); // needs to be enabled to be dismissed
//            return view;
//        }
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.dialog_positive_btn:
//                    if (mRecentSuggestions != null) {
//                        mRecentSuggestions.clearHistory();
//                        mRecentSuggestions = null;
//                        Utils.showSnackbar(mLayout, "History successfully cleared");
//                    }
//                    break;
//            }
//            dismiss();
//        }
//
//
//    }


}
