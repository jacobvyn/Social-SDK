package com.jacob.getsocial.reddit.data.local;

import android.support.annotation.NonNull;

import com.jacob.getsocial.common.AppExecutors;
import com.jacob.getsocial.reddit.data.PageDataSource;
import com.jacob.getsocial.reddit.data.model.Page;
import com.jacob.getsocial.reddit.data.model.PageDao;

public class LocalPageDataSource implements PageDataSource {
    private static LocalPageDataSource sInstance;
    private final PageDao mPageDao;
    private final AppExecutors mExecutors;

    private LocalPageDataSource(PageDao pageDao, AppExecutors executor) {
        this.mPageDao = pageDao;
        this.mExecutors = executor;
    }

    public static PageDataSource getInstance(PageDao pageDao, AppExecutors executor) {
        if (sInstance == null) {
            sInstance = new LocalPageDataSource(pageDao, executor);
        }
        return sInstance;
    }

    @Override
    public void updatePage(@NonNull String query, @NonNull LoadPageCallback callback) {
        // do nothing
    }

    @Override
    public void savePage(@NonNull final Page page, @NonNull final SavePageCallback callback) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                boolean pageExisted = mPageDao.savePage(page);
                callBackOnUi(callback, pageExisted);
            }
        };
        mExecutors.disk().execute(action);
    }

    private void callBackOnUi(final SavePageCallback callback, final boolean pageExisted) {
        if (callback == null) return;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (pageExisted) {
                    callback.onPageAlreadyExists();
                } else {
                    callback.onPageSaved();
                }
            }
        };
        mExecutors.ui().execute(runnable);
    }

    @Override
    public void loadNextPage(@NonNull final String query, @NonNull final String nextPage, @NonNull final LoadPageCallback callback) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                Page page = mPageDao.loadNextPage(query, nextPage);
                callBackOnUi(page, callback);
            }
        };
        mExecutors.disk().execute(action);
    }

    @Override
    public void loadPage(@NonNull final String query, @NonNull final LoadPageCallback callback) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                Page page = mPageDao.loadPage(query);
                callBackOnUi(page, callback);
            }
        };
        mExecutors.disk().execute(action);
    }

    private void callBackOnUi(final Page page, final LoadPageCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (page == null) {
                    callback.onNothingFound("Nothing found in DB");
                } else {
                    callback.onPageLoaded(page);
                }
            }
        };
        mExecutors.ui().execute(runnable);
    }
}
