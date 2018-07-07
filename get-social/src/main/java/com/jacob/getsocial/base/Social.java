package com.jacob.getsocial.base;

public interface Social {
    /**
     * Resumes network operations.
     * Call this method  when Activity is about in resume state.
     */
    void onResume();

    /**
     * Pause network operations.
     * Call this method  when Activity is about in pause state.
     */
    void onPause();

    /**
     * Use this method in case of fine-grained integration, to provide data you would like to search for.
     *
     * @param query - expression to search for
     */
    void searchFor(String query);

    /**
     * Hides Search view of SDK.
     * Call this method if you would like to use your own search view implementation.
     */
    void hideSearchView();

    enum Type {
        REDDIT
    }
}
