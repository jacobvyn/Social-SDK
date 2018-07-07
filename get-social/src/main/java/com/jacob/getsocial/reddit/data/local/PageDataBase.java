package com.jacob.getsocial.reddit.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jacob.getsocial.reddit.data.model.Page;
import com.jacob.getsocial.reddit.data.model.PageDao;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageDataBase {
    private static final Map<String, Page> PAGE_DATA_BASE = new LinkedHashMap<>();

    private static PageDataBase sInstance;
    private PageDao mPageDao;

    private PageDataBase() {
        mPageDao = new PageDaoImpl();
    }

    public static PageDataBase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PageDataBase();
        }
        return sInstance;
    }

    public PageDao getPageDao() {
        return mPageDao;
    }

    private class PageDaoImpl implements PageDao {

        @Override
        public boolean savePage(@NonNull Page page) {
            Page page1Exists = PAGE_DATA_BASE.put(page.getQuery(), page);
            Page page2Exists = PAGE_DATA_BASE.put(page.getId(), page);
            return page1Exists != null || page2Exists != null;
        }

        @Override
        public Page loadNextPage(@NonNull String query, @NonNull String nextPage) {
            String id = query + nextPage;
            return PAGE_DATA_BASE.get(id);
        }

        @Override
        public Page loadPage(@NonNull String query) {
            return PAGE_DATA_BASE.get(query);
        }
    }
}
