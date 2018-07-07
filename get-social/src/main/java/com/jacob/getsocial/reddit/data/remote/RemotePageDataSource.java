package com.jacob.getsocial.reddit.data.remote;

import android.support.annotation.NonNull;

import com.jacob.getsocial.common.AppExecutors;
import com.jacob.getsocial.reddit.data.PageDataSource;
import com.jacob.getsocial.reddit.data.model.Page;

public class RemotePageDataSource implements PageDataSource {
    private static RemotePageDataSource sInstance;
    private final AppExecutors mExecutors;
    private final RedditService mService;

    private RemotePageDataSource(AppExecutors executors) {
        mExecutors = executors;
        mService = new RedditService();
    }

    public static RemotePageDataSource getInstance(AppExecutors executors) {
        if (sInstance == null) {
            sInstance = new RemotePageDataSource(executors);
        }
        return sInstance;
    }

    @Override
    public void updatePage(@NonNull String query, @NonNull LoadPageCallback callback) {
        loadPage(query, callback);
    }

    @Override
    public void savePage(@NonNull Page page, @NonNull SavePageCallback callback) {
        //send to server new data, comments etc
    }

    @Override
    public void loadNextPage(@NonNull final String query, @NonNull final String nextPage, @NonNull final LoadPageCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RedditResponse redditResponse = mService.loadNextPage(query, nextPage);
                callbackOnUi(redditResponse, callback);
            }
        };
        mExecutors.network().execute(runnable);
    }

    @Override
    public void loadPage(@NonNull final String query, @NonNull final LoadPageCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RedditResponse redditResponse = mService.loadPage(query);
                callbackOnUi(redditResponse, callback);
            }
        };

        mExecutors.network().execute(runnable);
    }

    private void callbackOnUi(final RedditResponse redditResponse, final LoadPageCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (redditResponse.isSuccessful()) {
                    callback.onPageLoaded(redditResponse.getPage());
                } else {
                    String reason = redditResponse.getMessage();
                    callback.onNothingFound(reason);
                }
            }
        };
        mExecutors.ui().execute(runnable);
    }
}
