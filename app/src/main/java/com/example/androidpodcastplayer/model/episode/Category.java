package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(strict = false)
public class Category implements Serializable{

    @Attribute(name = "text", required = false)
    private String category;

    public Category() {}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
