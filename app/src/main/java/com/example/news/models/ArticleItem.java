package com.example.news.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rim on 10/03/2018.
 */

public class ArticleItem {

    private String title;
    private String description;

    @SerializedName("url")
    private String URL;

    @SerializedName("urlToImage")
    private String imageURL;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}