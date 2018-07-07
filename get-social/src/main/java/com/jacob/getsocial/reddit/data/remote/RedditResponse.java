package com.jacob.getsocial.reddit.data.remote;

import com.jacob.getsocial.reddit.data.model.Page;

public class RedditResponse {
    private Page mPage;
    private String mMessage;
    private boolean mIsSuccessful;
    private String mId;

    public RedditResponse(String query, String nextPageToken) {
        mId = query + nextPageToken;
    }

    public void setSuccessful(boolean isSuccessful) {
        this.mIsSuccessful = isSuccessful;
    }

    public void setPage(Page page) {
        this.mPage = page;
        mPage.setId(mId);
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public Page getPage() {
        return mPage;
    }

    public String getMessage() {
        return mMessage;
    }
}
