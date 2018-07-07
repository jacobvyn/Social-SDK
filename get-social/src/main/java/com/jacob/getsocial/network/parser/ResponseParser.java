package com.jacob.getsocial.network.parser;

import com.jacob.getsocial.common.Logging;
import com.jacob.getsocial.network.HttpRawResponse;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.data.model.Page;
import com.jacob.getsocial.reddit.data.remote.RedditResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ResponseParser extends BaseJSONParser {
    private static final String REASON_TEMPLATE = "Server code: %1d., message: %2s.";
    private static final String BROKEN_RESPONSE = "Server's response is broken.";
    private static final String PARAM_DATA = "data";
    private static final String PARAM_AFTER = "after";
    private static final String PARAM_CHILDREN = "children";
    private static final String PARAM_NEWS_AUTHOR = "author";
    private static final String PARAM_NEWS_URL = "url";
    private static final String PARAM_NEWS_THUMBNAIL = "thumbnail";
    private static final String PARAM_NEWS_TITLE = "title";
    private static final String PARAM_NEWS_NUM_COMMENTS = "num_comments";
    private static final String PARAM_NEWS_UPS = "ups";

    public RedditResponse parse(HttpRawResponse httpResponse, String query, String nextPageToken) {
        RedditResponse redditResponse = new RedditResponse(query, nextPageToken);
        if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
            if (isBodyParcelable(httpResponse)) {
                redditResponse.setSuccessful(true);
                redditResponse.setPage(parsePage(httpResponse.getBody()));
            } else {
                redditResponse.setMessage(BROKEN_RESPONSE);
            }
        } else {
            redditResponse.setSuccessful(false);
            String reason = "Server code: " + httpResponse.getCode() + ", message: " + httpResponse.getMessage();
            redditResponse.setMessage(String.format(REASON_TEMPLATE, httpResponse.getCode(), httpResponse.getMessage()));
        }
        return redditResponse;
    }

    private Page parsePage(byte[] body) {
        Page page = new Page();
        JSONObject response = parseToJsonObject(body);

        try {
            JSONObject dataObj = response.getJSONObject(PARAM_DATA);
            String nextPage = getString(dataObj, PARAM_AFTER, false);
            page.setNextPageToken(nextPage);
            JSONArray children = dataObj.getJSONArray(PARAM_CHILDREN);

            List<News> newsList = new ArrayList<>();
            for (int i = 0; i < children.length(); i++) {
                JSONObject child = children.getJSONObject(i).getJSONObject(PARAM_DATA);
                News news = parseNews(child);
                newsList.add(news);
            }
            page.setNewsList(newsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return page;
    }

    private News parseNews(JSONObject child) throws JSONException {
        String author = getString(child, PARAM_NEWS_AUTHOR, false);
        String url = getString(child, PARAM_NEWS_URL, false);
        String thumbnail = getString(child, PARAM_NEWS_THUMBNAIL, false);
        String title = getString(child, PARAM_NEWS_TITLE, false);
        int comments = getInt(child, PARAM_NEWS_NUM_COMMENTS, false);
        int likes = getInt(child, PARAM_NEWS_UPS, false);
        return new News(author, url, comments, likes, thumbnail, title);
    }

    private boolean isBodyParcelable(HttpRawResponse response) {
        byte[] body = response.getBody();
        return body != null && isParcelableAsJson(body);
    }

    private static boolean isParcelableAsJson(byte[] data) {
        return parseToJsonObject(data) != null;
    }

    private static JSONObject parseToJsonObject(byte[] data) {
        try {
            String bodyAsString = new String(data).intern();
            return new JSONObject(bodyAsString);
        } catch (JSONException e) {
            Logging.out(ResponseParser.class.getSimpleName(), e.getMessage());
        } catch (UnsupportedOperationException e) {
            Logging.out(ResponseParser.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

}
