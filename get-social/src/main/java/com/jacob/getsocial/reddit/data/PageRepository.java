package com.jacob.getsocial.reddit.data;

import android.support.annotation.NonNull;

import com.jacob.getsocial.reddit.data.model.Page;

public class PageRepository implements PageDataSource {

    private final PageDataSource mLocalDataSource;
    private final PageDataSource mRemoteDataSource;
    private static PageRepository sInstance;

    private PageRepository(PageDataSource localDataSource, PageDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static PageRepository getInstance(PageDataSource localDataSource, PageDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new PageRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void updatePage(@NonNull String query, @NonNull final LoadPageCallback callback) {
        mRemoteDataSource.updatePage(query, new LoadPageCallback() {
            @Override
            public void onPageLoaded(Page page) {
                savePageAndCallBack(page, callback);
            }

            @Override
            public void onNothingFound(String reason) {
                callback.onNothingFound(reason);
            }
        });
    }

    @Override
    public void savePage(@NonNull Page page, @NonNull SavePageCallback callback) {
        mLocalDataSource.savePage(page, callback);
    }

    @Override
    public void loadNextPage(@NonNull final String query, @NonNull final String nextPage, @NonNull final LoadPageCallback callback) {
        mLocalDataSource.loadNextPage(query, nextPage, new LoadPageCallback() {
            @Override
            public void onPageLoaded(Page page) {
                callback.onPageLoaded(page);
            }

            @Override
            public void onNothingFound(String reason) {
                attemptToLoadNextPageRemote(query, nextPage, callback);
            }
        });
    }

    @Override
    public void loadPage(@NonNull final String query, @NonNull final LoadPageCallback callback) {
        mLocalDataSource.loadPage(query, new LoadPageCallback() {
            @Override
            public void onPageLoaded(Page page) {
                callback.onPageLoaded(page);
            }

            @Override
            public void onNothingFound(String reason) {
                attemptToLoadPageRemote(query, callback);
            }
        });
    }

    private void attemptToLoadNextPageRemote(String query, String nextPage, final LoadPageCallback callback) {
        mRemoteDataSource.loadNextPage(query, nextPage, new LoadPageCallback() {
            @Override
            public void onPageLoaded(Page page) {
                callback.onPageLoaded(page);
            }

            @Override
            public void onNothingFound(String reason) {
                callback.onNothingFound(reason);
            }
        });
    }

    private void attemptToLoadPageRemote(String query, final LoadPageCallback callback) {
        mRemoteDataSource.loadPage(query, new LoadPageCallback() {
            @Override
            public void onPageLoaded(final Page page) {
                callback.onPageLoaded(page);
                savePage(page, null);
            }

            @Override
            public void onNothingFound(String reason) {
                callback.onNothingFound(reason);
            }
        });
    }

    private void savePageAndCallBack(final Page page, final LoadPageCallback callback) {
        savePage(page, new SavePageCallback() {
            @Override
            public void onPageSaved() {
                callback.onPageLoaded(page);
            }

            @Override
            public void onPageAlreadyExists() {
                callback.onNothingFound("Already up to date.");
            }
        });
    }
}
