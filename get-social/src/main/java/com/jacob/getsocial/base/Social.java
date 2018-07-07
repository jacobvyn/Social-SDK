package com.jacob.getsocial.base;

public interface Social {
    void onResume();

    void onPause();

    void searchFor(String query);

    enum Type {
        REDDIT
    }
}
