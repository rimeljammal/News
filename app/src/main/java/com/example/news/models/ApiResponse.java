package com.example.news.models;

import java.util.List;

/**
 * Created by Rim on 07/03/2018.
 */

public class ApiResponse {

    private List<ArticleItem> articles;

    public int getCount()   {
        return articles.size();
    }

    public List<ArticleItem> getArticles()  {
        return articles;
    }

}