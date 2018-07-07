package com.jacob.getsocial.reddit.data.model;

import android.support.annotation.NonNull;

public interface PageDao {
    boolean savePage(@NonNull Page page);

    Page loadNextPage(@NonNull String query, @NonNull String nextPage);

    Page loadPage(@NonNull String query);
}
