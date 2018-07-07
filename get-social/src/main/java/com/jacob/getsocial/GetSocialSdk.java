package com.jacob.getsocial;

import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.jacob.getsocial.base.GetSocial;
import com.jacob.getsocial.reddit.view.GetSocialRedditImpl;

public class GetSocialSdk {

    public static GetSocial getInstance(AppCompatActivity activity, FrameLayout container, Type type) {
        switch (type) {
            case REDDIT: {
                return new GetSocialRedditImpl(activity, container);
            }
            default: {
                throw new IllegalArgumentException("Another social type  currently are not supported!");
            }
        }
    }

    public enum Type {
        REDDIT
    }
}
