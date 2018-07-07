package com.jacob.getsocial.reddit.view;

import android.app.Activity;
import android.content.Intent;

import com.jacob.getsocial.reddit.GetSocialRedditContract;
import com.jacob.getsocial.reddit.data.PageDataSource;
import com.jacob.getsocial.reddit.data.PageRepository;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.data.model.Page;

public class RedditPresenter implements GetSocialRedditContract.Presenter, PageDataSource.LoadPageCallback {
    private final GetSocialRedditContract.View mView;
    private final Activity mActivity;
    private final PageRepository mRepository;
    private String mNextPage;
    private String mLastQuery;
    private LoadState mState = LoadState.NONE;

    public RedditPresenter(GetSocialRedditContract.View view, Activity activity, PageRepository repository) {
        mView = view;
        mActivity = activity;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void updatePage() {
        if (isNoneState()) {
            if (mLastQuery != null) {
                mState = LoadState.UPDATE;
                mView.showProgressBar(true);
                mRepository.updatePage(mLastQuery, this);
            } else {
                mView.showProgressBar(false);
                mView.showMessage("Search query is empty.");
            }
        }
    }

    @Override
    public void onItemCLicked(News news) {
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(DetailActivity.ARG_NEWS, news);
        mActivity.startActivity(intent);
    }

    @Override
    public void onPageLoaded(Page page) {
        if (mState == LoadState.PRELOAD) {
            mView.onNewsPreloaded(page.getNewsList());
        } else if (mState == LoadState.UPDATE) {
            mView.onUpdate(page.getNewsList());
        }
        mNextPage = page.getNextPageToken();
        mView.showProgressBar(false);
        mState = LoadState.NONE;
    }

    @Override
    public void loadPage(String query) {
        mState = LoadState.UPDATE;
        mLastQuery = query;
        mView.showProgressBar(true);
        mRepository.loadPage(query, this);
    }

    @Override
    public void loadNextPage() {
        if (isNoneState()) {
            mState = LoadState.PRELOAD;
            mRepository.loadNextPage(mLastQuery, mNextPage, this);
        }
    }

    @Override
    public void onNothingFound(String reason) {
        mView.showProgressBar(false);
        mView.showMessage(reason);
        mState = LoadState.NONE;
    }

    private boolean isNoneState() {
        return mState == LoadState.NONE;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private enum LoadState {
        UPDATE, PRELOAD, NONE
    }
}
