package com.jacob.getsocial.reddit.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jacob.getsocial.R;
import com.jacob.getsocial.common.UiUtils;
import com.jacob.getsocial.reddit.GetSocialRedditContract;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.view.custom.SearchView;

import java.util.List;

public class RedditView extends RelativeLayout implements
        GetSocialRedditContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.OnItemClickListener, SearchView.OnSearchListener {

    private static final double THRESHOLD = 0.8;
    private GetSocialRedditContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SearchView mSearchView;

    public RedditView(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.reddit_layout, null);
        addView(view, UiUtils.createMatchParentParam());
        initView(view);
    }

    private void initView(final View view) {
        mSearchView =(SearchView) view.findViewById(R.id.reddit_search_view);
        mSearchView.setListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.reddit_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.black, R.color.black, R.color.black, R.color.black);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.reddit_recycler_view);
        mAdapter = new NewsAdapter(this);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RedditView.this.onScrolled();
            }
        });
    }

    @Override
    public void showProgressBar(boolean show) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(show);
        }
    }

    @Override
    public void scrollToStart() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onNewsPreloaded(List<News> newsList) {
        mAdapter.addNewsToTail(newsList);
    }

    @Override
    public void onUpdate(List<News> newsList) {
        mAdapter.addNewsToHead(newsList);
        scrollToStart();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void clearData() {
        mAdapter.clearData();
    }

    @Override
    public void setPresenter(GetSocialRedditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.updatePage();
        }
    }

    private void onScrolled() {
        int lastVisiblePos = mLayoutManager.findLastCompletelyVisibleItemPosition();
        if (isNeedPreloadNextPage(lastVisiblePos)) {
            mPresenter.loadNextPage();
        }
    }

    private boolean isNeedPreloadNextPage(int pos) {
        return pos >= 0 && pos > mAdapter.getItemCount() * THRESHOLD;
    }

    @Override
    public void onItemClicked(News news) {
        mPresenter.onItemCLicked(news);
    }

    public void hideSearchView() {
        mSearchView.setVisibility(GONE);
    }

    @Override
    public void onSearch(String expression) {
        mPresenter.loadPage(expression);
    }
}
