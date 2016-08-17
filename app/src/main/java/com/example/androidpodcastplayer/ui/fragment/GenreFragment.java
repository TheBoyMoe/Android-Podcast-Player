package com.example.androidpodcastplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidpodcastplayer.R;
import com.example.androidpodcastplayer.common.Constants;
import com.example.androidpodcastplayer.common.Utils;
import com.example.androidpodcastplayer.model.Podcast;
import com.example.androidpodcastplayer.model.Results;
import com.example.androidpodcastplayer.rest.ApiClient;
import com.example.androidpodcastplayer.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class GenreFragment extends ContractFragment<GenreFragment.Contract>{

    public interface Contract {
        void onItemClick();
        void downloadError(String message);
    }

    private TextView mEmptyView;

    public GenreFragment() {}

    public static GenreFragment newInstance(int genreId) {
        GenreFragment fragment = new GenreFragment();
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
        View view = inflater.inflate(R.layout.content_podcast_recycler, container, false);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        if (savedInstanceState == null) {
            displayContent();
        }
        return view;
    }

    private void displayContent() {
        int genreId = getArguments().getInt(Constants.GENRE_ID);
        if (genreId > 0) {
            executeGenreQuery(genreId);
            mEmptyView.setVisibility(View.GONE);
        } else {
            getContract().downloadError("Error downloading podcasts");
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void executeGenreQuery(int genreId) {
        ApiInterface restService = ApiClient.getClient().create(ApiInterface.class);
        Call<Results> call = restService.getCategoryPodcasts(
                Constants.REST_TERM, genreId, Constants.REST_LIMIT
        );
        call.enqueue(new Callback<Results>() { // execute call asynchronously
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                List<Podcast> results = response.body().getResults();
                for (Podcast result : results) {
                    Timber.i("%s: artist name: %s", Constants.LOG_TAG, result.getArtistName());
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Timber.e("%s error %s", Constants.LOG_TAG, t.getMessage());
                getContract().downloadError(t.getMessage());
            }
        });
    }


}
