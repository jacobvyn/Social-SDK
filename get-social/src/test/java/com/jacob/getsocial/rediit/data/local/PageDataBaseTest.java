package com.jacob.getsocial.rediit.data.local;

import android.content.Context;

import com.jacob.getsocial.reddit.data.local.PageDataBase;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.data.model.Page;
import com.jacob.getsocial.reddit.data.model.PageDao;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PageDataBaseTest {
    @Mock
    private Context mContext;

    @Test
    public void db_loadPageByQuery() {
        PageDao cache = PageDataBase.getInstance(mContext).getPageDao();
        String query = "lamborghini";
        String netPageToken = "bwm";
        Page page = createPage(query, netPageToken);
        cache.savePage(page);

        Page loadedPageNull = cache.loadPage(query + "diablo");
        assertTrue(loadedPageNull == null);

        Page loadedPageNotNull = cache.loadPage(page.getId());
        assertEquals(page, loadedPageNotNull);
    }

    @Test
    public void db_loadPage_by_query_and_nextPageToken() {
        PageDao pageDao = PageDataBase.getInstance(mContext).getPageDao();
        String query = "android";
        String netPageToken = "next_page_ios";
        Page page = createPage(query, netPageToken);
        pageDao.savePage(page);

        Page loadedPageNull = pageDao.loadNextPage("ios", "next_page_100500");
        assertTrue(loadedPageNull == null);

        Page loadedPageNotNull = pageDao.loadNextPage(query, netPageToken);
        assertEquals(page, loadedPageNotNull);
    }

    @Test
    public void db_savePage_return_if_page_exist() {
        PageDao dao = PageDataBase.getInstance(mContext).getPageDao();
        String query = "earth";
        String netPageToken = "next_page_mars";
        Page page = createPage(query, netPageToken);

        boolean pageShouldNotExist = dao.savePage(page);
        assertFalse(pageShouldNotExist);

        boolean pageShouldExist = dao.savePage(page);
        assertTrue(pageShouldExist);
    }

    private Page createPage(String query, String nextPageToken) {
        News newsJacob = new News("jacob", "http://jacob.com", 666, 777, "http://jacob.com/avatar.jpg", "test");
        News newsAlex = new News("alex", "http://alex.com", 111, 222, "http://alex.com/avatar.jpg", "test");
        List<News> newsList = Arrays.asList(newsAlex, newsJacob);

        Page page = new Page();
        page.setNextPageToken(nextPageToken);
        page.setId(query + nextPageToken);
        page.setNewsList(newsList);
        return page;
    }
}