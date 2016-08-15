package com.example.androidpodcastplayer.custom;

import android.content.SearchRecentSuggestionsProvider;

public class QuerySuggestionProvider extends SearchRecentSuggestionsProvider{

    public static final String AUTHORITY =
        "com.example.androidpodcastplayer.custom.QuerySuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public QuerySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
