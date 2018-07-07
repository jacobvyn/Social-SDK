package com.jacob.getsocial.common;

import android.text.TextUtils;
import android.util.Log;

import com.jacob.getsocial.BuildConfig;


public class Logging {

    private static final String PREFIX = "Debug.Jacob.";

    private Logging() {
    }

    public static void out(String tag, final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        final String logTag = PREFIX + tag;
        if (BuildConfig.DEBUG) {
            Log.i(logTag, text);
        }
    }
}
