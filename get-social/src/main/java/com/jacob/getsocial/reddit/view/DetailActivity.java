package com.jacob.getsocial.reddit.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jacob.getsocial.R;
import com.jacob.getsocial.reddit.data.model.News;
import com.jacob.getsocial.reddit.view.custom.ImageNewsFragment;
import com.jacob.getsocial.reddit.view.custom.WebNewsFragment;

public class DetailActivity extends AppCompatActivity {
    public static final String ARG_NEWS = "ARG_NEWS";
    private News mNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_activity_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            mNews = getIntent().getParcelableExtra(ARG_NEWS);
        }
        if (mNews != null) {
            getSupportActionBar().setTitle(mNews.getTitle());
            addContent();
        } else {
            finish();
        }
    }

    private void addContent() {
        Fragment fragment;
        if (mNews.isImage()) {
            fragment = ImageNewsFragment.newInstance(mNews);
        } else {
            fragment = WebNewsFragment.newInstance(mNews);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detail_activity_container, fragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
