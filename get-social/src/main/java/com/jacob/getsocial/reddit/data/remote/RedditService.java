package com.jacob.getsocial.reddit.data.remote;

import android.support.annotation.NonNull;

import com.jacob.getsocial.BuildConfig;
import com.jacob.getsocial.network.HttpRawResponse;
import com.jacob.getsocial.network.HttpUtils;
import com.jacob.getsocial.network.parser.ResponseParser;

public class RedditService {
    private static final ResponseParser PARSER = new ResponseParser();

    @NonNull
    public RedditResponse loadNextPage(String query, String nextPage) {
        String url = BuildConfig.REDDIT_OAUTH_BASE_URL + "q=" + query + "&after=" + nextPage;
        HttpRawResponse httpRawResponse = HttpUtils.doRequest(url);
        return PARSER.parse(httpRawResponse, query);
    }

    @NonNull
    public RedditResponse loadPage(String query) {
//        String url = BuildConfig.REDDIT_OAUTH_BASE_URL + "q=" + query;
        String url = "https://oauth.reddit.com/api/v1/me";
//        String url = BuildConfig.REDDIT_OAUTH_BASE_URL + "Bitcoin/search/" + "q=" + query;
        HttpRawResponse httpRawResponse = HttpUtils.doRequest(url);
        return PARSER.parse(httpRawResponse, query);
    }
}
