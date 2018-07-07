package com.jacob.getsocial.reddit.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.jacob.getsocial.network.HttpUtils;
import com.jacob.getsocial.base.Social;
import com.jacob.getsocial.reddit.GetSocialRedditContract;
import com.jacob.getsocial.reddit.RedditPresenter;
import com.jacob.getsocial.reddit.data.RepoProvider;

public class GetSocialRedditImpl implements Social {
    private final GetSocialRedditContract.Presenter mRedditPresenter;
    private final RedditView mRedditView;

    public GetSocialRedditImpl(AppCompatActivity activity, FrameLayout container) {
        if (activity == null || container == null) {
            throw new IllegalArgumentException("Activity or FrameLayout  should not be null");
        }

        mRedditView = new RedditView(activity);
        mRedditPresenter = new RedditPresenter(mRedditView, activity, RepoProvider.provideRepository(activity));

        HttpUtils.setUserAgent(activity);
        container.removeAllViews();
        container.addView(mRedditView);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        mRedditPresenter.onResume();
    }

    @Override
    public void onPause() {
        mRedditPresenter.onPause();
    }

    @Override
    public void searchFor(String query) {
        mRedditPresenter.loadPage(query);
    }

    @Override
    public void hideSearchView() {
        mRedditView.hideSearchView();
    }
}
