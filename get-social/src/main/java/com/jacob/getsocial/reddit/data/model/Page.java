package com.jacob.getsocial.reddit.data.model;

import java.util.List;
import java.util.Objects;

public class Page {
    private String mNextPageToken = "";
    private String mId = "";

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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(mNextPageToken, page.mNextPageToken) &&
                Objects.equals(mId, page.mId) &&
                Objects.equals(mNewsList, page.mNewsList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mNextPageToken, mId, mNewsList);
    }
}
