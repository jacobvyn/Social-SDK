package com.jacob.getsocial;

import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.jacob.getsocial.base.Social;
import com.jacob.getsocial.reddit.view.GetSocialRedditImpl;

public class SocialSdk {

    public static Social getInstance(AppCompatActivity activity, FrameLayout container, Social.Type type) {
        switch (type) {
            case REDDIT: {
                return new GetSocialRedditImpl(activity, container);
            }
            default: {
                throw new IllegalArgumentException("Another social type  currently are not supported!");
            }
        }
    }

}
