package com.jacob.getsocial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.jacob.getsocial.base.Social;
import com.jacob.getsocial.reddit.view.GetSocialRedditImpl;

public class SocialSdk {

    /**
     * Method to get <code >Social.class</code> instance
     *
     * @param activity  reference to your Activity.class
     * @param container reference to container for view.
     * @param type      preferable type of social network.
     * @return
     */
    public static Social getInstance(@NonNull AppCompatActivity activity, @NonNull FrameLayout container, @NonNull Social.Type type) {
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
