package com.jacob.getsocial.reddit.view.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.getsocial.R;
import com.jacob.getsocial.reddit.data.model.News;

public class WebNewsFragment extends BaseFragment implements View.OnKeyListener {
    private BaseWebView mWebView;

    public static WebNewsFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        WebNewsFragment fragment = new WebNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_news, container, false);
        setBackPressListener(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mWebView = (BaseWebView) view.findViewById(R.id.detail_activity_web_view);
        mWebView.loadUrl(mNews.getUrl());
    }


    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    private void setBackPressListener(@NonNull View rootView) {
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK && onWebViewBackPressed();
    }

    private boolean onWebViewBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.loadUrl("about:blank");
        mWebView.destroy();
    }
}
