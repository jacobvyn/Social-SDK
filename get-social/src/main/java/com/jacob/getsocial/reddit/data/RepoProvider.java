package com.jacob.getsocial.reddit.data;

import android.content.Context;

import com.jacob.getsocial.common.AppExecutors;
import com.jacob.getsocial.reddit.data.local.LocalPageDataSource;
import com.jacob.getsocial.reddit.data.local.PageDataBase;
import com.jacob.getsocial.reddit.data.remote.RemotePageDataSource;

public class RepoProvider {
    public static RedditRepository provideRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context should not be null!");
        }

        PageDataBase dataBase = PageDataBase.getInstance(context);
        AppExecutors executors = new AppExecutors();
        return RedditRepository.getInstance(
                LocalPageDataSource.getInstance(dataBase.getPageDao(),executors ),
                RemotePageDataSource.getInstance(executors));
    }
}
