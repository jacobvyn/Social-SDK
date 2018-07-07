package com.jacob.getsocial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jacob.getsocial.base.Social;

/**
 * Created by vynnykiakiv on 7/2/18.
 */
public class SocialDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static final String SOCIAL_TYPE = "SOCIAL_TYPE";
    private Social mReddit;
    private Social.Type mSocialType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_social_demo);
        Toolbar bar = findViewById(R.id.get_social_activity_action_bar);
        bar.setTitle(R.string.reddit);
        setSupportActionBar(bar);

        FrameLayout container = (FrameLayout) findViewById(R.id.root_container);
        setSocialType();
        mReddit = SocialSdk.getInstance(this, container, mSocialType);
        mReddit.hideSearchView();

        String keyWord = "money";
        mReddit.searchFor(keyWord);
        getSupportActionBar().setTitle(keyWord);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.getItem(0);
        if (item.getActionView() instanceof SearchView) {
            ((SearchView) item.getActionView()).setOnQueryTextListener(this);
        }
        return true;
    }

    private void setSocialType() {
        if (getIntent() != null) {
            mSocialType = (Social.Type) getIntent().getSerializableExtra(SOCIAL_TYPE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReddit.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReddit.onPause();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mReddit.searchFor(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
