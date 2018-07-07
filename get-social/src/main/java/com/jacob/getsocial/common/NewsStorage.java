package com.jacob.getsocial.common;

import com.jacob.getsocial.reddit.data.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsStorage {
    private final List<News> mData = new ArrayList<>();

    public void addAll(int index, List<News> newsList) {
        newsList.removeAll(mData);
        mData.addAll(index, newsList);
    }

    public void addAll(List<News> newsList) {
        newsList.removeAll(mData);
        mData.addAll(newsList);
    }

    public News get(int position) {
        return mData.get(position);
    }

    public int size() {
        return mData.size();
    }
}
