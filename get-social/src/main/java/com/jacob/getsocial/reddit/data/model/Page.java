package com.jacob.getsocial.reddit.data.model;

import java.util.List;

public class Page {
    private String mNextPageToken = "";
    private String mQuery = "";

    public String getNextPageToken() {
        return mNextPageToken;
    }

    public void setNextPageToken(String mNextPage) {
        this.mNextPageToken = mNextPage;
    }

    public List<News> getNewsList() {
        return mNewsList;
    }

    public void setNewsList(List<News> mNewsList) {
        this.mNewsList = mNewsList;
    }

    private List<News> mNewsList;

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getId() {
        return mQuery + mNextPageToken;
    }
}
