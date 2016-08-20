package com.example.androidpodcastplayer.model.podcast;

/*
    Search results: https://itunes.apple.com/search?term=paleo&entity=podcast&limit=10
    {
        "resultCount":10,
            "results":[
                {
                    "wrapperType" : "track",
                    "kind" : "podcast",
                    "collectionId" : 340221970,
                    "trackId" : 340221970,
                    "artistName" : "Robb Wolf",
                    "collectionName" : "Robb Wolf - The Paleo Solution Podcast - Paleo diet, nutrition, fitness, and health",
                    "trackName" : "Robb Wolf - The Paleo Solution Podcast - Paleo diet, nutrition, fitness, and health",
                    "collectionCensoredName" : "Robb Wolf - The Paleo Solution Podcast - Paleo diet, nutrition, fitness, and health",
                    "trackCensoredName" : "Robb Wolf - The Paleo Solution Podcast - Paleo diet, nutrition, fitness, and health",
                    "collectionViewUrl" : "https://itunes.apple.com/us/podcast/robb-wolf-paleo-solution-podcast/id340221970?mt=2&uo=4",
                    "feedUrl" : "http://robbwolf.libsyn.com/rss",
                    "trackViewUrl" : "https://itunes.apple.com/us/podcast/robb-wolf-paleo-solution-podcast/id340221970?mt=2&uo=4",
                    "artworkUrl30" : "http://is1.mzstatic.com/image/thumb/Music1/v4/da/87/99/da879993-d468-616f-518e-b0bc59259a35/source/30x30bb.jpg",
                    "artworkUrl60" : "http://is1.mzstatic.com/image/thumb/Music1/v4/da/87/99/da879993-d468-616f-518e-b0bc59259a35/source/60x60bb.jpg",
                    "artworkUrl100" : "http://is1.mzstatic.com/image/thumb/Music1/v4/da/87/99/da879993-d468-616f-518e-b0bc59259a35/source/100x100bb.jpg",
                    "collectionPrice" : 0.00,
                    "trackPrice" : 0.00,
                    "trackRentalPrice" : 0,
                    "collectionHdPrice" : 0,
                    "trackHdPrice" : 0,
                    "trackHdRentalPrice" : 0,
                    "releaseDate" : "2016-08-09T04:00:00Z",
                    "collectionExplicitness" : "explicit",
                    "trackExplicitness" : "explicit",
                    "trackCount" : 298,
                    "country" : "USA",
                    "currency" : "USD",
                    "primaryGenreName" : "Fitness & Nutrition",
                    "contentAdvisoryRating" : "Explicit",
                    "artworkUrl600" : "http://is1.mzstatic.com/image/thumb/Music1/v4/da/87/99/da879993-d468-616f-518e-b0bc59259a35/source/600x600bb.jpg",
                    "genreIds":[
                            "1417",
                            "26",
                            "1307"
                    ],
                    "genres":[
                            "Fitness & Nutrition",
                            "Podcasts",
                            "Health"
                    ]
                },
                {},
                {}

            ]
    }
    example: https://itunes.apple.com/search?term=podcast&entity=podcast&genreId=26&limit=5



*/

import java.util.ArrayList;
import java.util.List;

public class Results {

    private int resultCount;
    private List<Podcast> results;

    public Results() {
        results = new ArrayList<>();
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<Podcast> getResults() {
        return results;
    }

    public void setResults(List<Podcast> results) {
        this.results = results;
    }


}

