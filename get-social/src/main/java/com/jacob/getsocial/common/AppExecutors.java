package com.jacob.getsocial.common;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final int THREADS_COUNT = 5;
    private final Executor mDiskExecutor;
    private final Executor mNetworkExecutor;
    private final Executor mUiExecutor;

    public AppExecutors(Executor diskIOExecutor, Executor networkIOExecutor, Executor uiExecutor) {
        this.mDiskExecutor = diskIOExecutor;
        this.mNetworkExecutor = networkIOExecutor;
        this.mUiExecutor = uiExecutor;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(THREADS_COUNT), new UiThreadExecutor());
    }

    public Executor disk() {
        return mDiskExecutor;
    }

    public Executor network() {
        return mNetworkExecutor;
    }

    public Executor ui() {
        return mUiExecutor;
    }

    private static class UiThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
