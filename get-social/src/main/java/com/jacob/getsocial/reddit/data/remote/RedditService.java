package com.jacob.getsocial.reddit.data.remote;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.jacob.getsocial.BuildConfig;
import com.jacob.getsocial.common.Utils;
import com.jacob.getsocial.network.HttpRawResponse;
import com.jacob.getsocial.network.HttpUtils;
import com.jacob.getsocial.network.parser.ResponseParser;

import java.util.HashMap;
import java.util.Map;

public class RedditService {
    private static final ResponseParser PARSER = new ResponseParser();
    private static final Map<String, String> HEADERS = new HashMap<>();
    private static final String KEY_GRANT_TYPE = "grant_type";
    private static final String KEY_AUTHORIZATION = "Authorization";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String PARAM_GRANT_TYPE = "installed_client";
    private static final String PARAM_DEVICE_ID = "4d7674f3-46bf-4352-9784-91e87c74d106";
    private static final String PARAM_BASIC_AUTHORIZATION = "Basic " + Utils.toBase64(BuildConfig.REDDIT_APP_ID);

    static {
        HEADERS.put(KEY_AUTHORIZATION, PARAM_BASIC_AUTHORIZATION);
        HEADERS.put(KEY_GRANT_TYPE, PARAM_GRANT_TYPE);
        HEADERS.put(KEY_DEVICE_ID, PARAM_DEVICE_ID);
    }

    @NonNull
    public RedditResponse loadNextPage(String query, String nextPageToken) {
        String url = BuildConfig.REDDIT_BASE_URL + query + "&after=" + nextPageToken;
        HttpRawResponse httpRawResponse = HttpUtils.doRequest(url, HEADERS);
        return PARSER.parse(httpRawResponse, query, nextPageToken);
    }

    @NonNull
    public RedditResponse loadPage(String query) {
        String url = BuildConfig.REDDIT_BASE_URL + query;
        HttpRawResponse httpRawResponse = HttpUtils.doRequest(url, HEADERS);
        return PARSER.parse(httpRawResponse, query, "");
    }
}
