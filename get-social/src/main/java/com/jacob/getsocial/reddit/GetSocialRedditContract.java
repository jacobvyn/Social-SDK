package com.jacob.getsocial.reddit;

import com.jacob.getsocial.base.BaseView;
import com.jacob.getsocial.reddit.data.model.News;

import java.util.List;

public interface GetSocialRedditContract {
    interface View extends BaseView<Presenter> {
        void showProgressBar(boolean show);

        void scrollToStart();

        void onNewsPreloaded(List<News> newsList);

        void onUpdate(List<News> newsList);

        void showMessage(String message);

        void clearData();
    }

    interface Presenter {

        void loadPage(String query);

        void loadNextPage();

        void onResume();

        void onPause();

        void updatePage();

        void onItemCLicked(News news);
    }
}
