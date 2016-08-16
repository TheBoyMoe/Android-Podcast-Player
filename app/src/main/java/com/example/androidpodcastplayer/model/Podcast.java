package com.example.androidpodcastplayer.model;

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

*/



public class Podcast {

    private String wrapperType;
    private String kind;
    private String collectionId;
    private String trackId;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String collectionViewUrl;
    private String feedUrl;
    private String trackViewUrl;
    private String artworkUrl100;
    private String artworkUrl600;
    private String releaseDate;
    private int trackCount;
    private String country;
    private String primaryGenreName;
    private String[] genreIds;
    private String[] genres;

    public Podcast() {}

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String[] genreIds) {
        this.genreIds = genreIds;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }


}
