package com.jacob.getsocial.reddit.view.custom;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jacob.getsocial.reddit.data.model.News;

public class BaseFragment extends Fragment {
    protected static final String ARG_NEWS = "ARG_NEWS";
    protected News mNews;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        retrieveNews();
    }

    private void retrieveNews() {
        if (getArguments() != null) {
            mNews = getArguments().getParcelable(ARG_NEWS);
        }
    }
}
