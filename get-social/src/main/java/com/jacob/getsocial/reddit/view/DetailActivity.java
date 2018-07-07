package com.jacob.getsocial.reddit.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jacob.getsocial.R;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.view.custom.BaseWebView;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public static final String ARG_NEWS = "ARG_NEWS";
    private BaseWebView mWebView;
    private ImageView mImageView;
    private News mNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_activity_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrieveNews();
        getSupportActionBar().setTitle(mNews.getTitle());
        mImageView = (ImageView) findViewById(R.id.detail_activity_image);
        mWebView = (BaseWebView) findViewById(R.id.detail_activity_web_view);
        loadData();
    }

    private void loadData() {
        if (mNews != null) {
            if (mNews.isImage()) {
                Picasso.get().load(mNews.getUrl()).into(mImageView);
            } else {
                mWebView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mWebView.loadUrl(mNews.getUrl());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void retrieveNews() {
        if (getIntent() != null) {
            mNews = getIntent().getParcelableExtra(ARG_NEWS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mNews.isWeb()) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
