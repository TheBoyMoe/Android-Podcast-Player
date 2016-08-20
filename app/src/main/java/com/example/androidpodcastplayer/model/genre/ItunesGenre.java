package com.example.androidpodcastplayer.model.genre;

public class ItunesGenre {

    private int genreId;
    private String title;
    private int drawable;

    public ItunesGenre(int genreId, String title, int drawable) {
        this.genreId = genreId;
        this.title = title;
        this.drawable = drawable;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

}
