package com.jacob.getsocial.reddit.data;

import android.support.annotation.NonNull;

import com.jacob.getsocial.reddit.data.model.Page;

public interface PageDataSource {
    void updatePage(@NonNull String query, @NonNull LoadPageCallback callback);

    void savePage(@NonNull Page page, @NonNull SavePageCallback callback);

    void loadNextPage(@NonNull String query, @NonNull String nextPage, @NonNull LoadPageCallback callback);

    void loadPage(@NonNull String query, @NonNull LoadPageCallback callback);

    interface LoadPageCallback {
        void onPageLoaded(Page page);

        void onNothingFound(String reason);
    }

    interface SavePageCallback {
        void onPageSaved();

        void onPageAlreadyExists();
    }

}
