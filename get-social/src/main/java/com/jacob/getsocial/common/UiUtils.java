package com.jacob.getsocial.common;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jacob.getsocial.R;

import java.util.Random;

public class UiUtils {
    private static final Random RANDOM = new Random();

    public static FrameLayout.LayoutParams createMatchParentParam() {
        return new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams createWrapContentParam(int gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        return params;
    }

    public static int getIconRes() {
        int res = RANDOM.nextInt(5);
        switch (res) {
            case 1: {
                return R.drawable.icon_web1;
            }
            case 2: {
                return R.drawable.icon_web2;
            }
            case 3: {
                return R.drawable.icon_web2;
            }
            case 4: {
                return R.drawable.icon_web2;
            }
            default: {
                return R.drawable.icon_web5;
            }
        }
    }
}
