package com.jacob.getsocial.reddit.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jacob.getsocial.R;
import com.jacob.getsocial.common.Utils;

public class SearchView extends FrameLayout implements View.OnClickListener, TextView.OnEditorActionListener {
    private OnSearchListener mListener;
    private EditText mSearchEditText;

    public SearchView(@NonNull Context context) {
        super(context);
        init();
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.search_layout, null);
        addView(view);
        mSearchEditText = (EditText) view.findViewById(R.id.search_edittext);
        view.findViewById(R.id.delete_search_button).setOnClickListener(this);
        mSearchEditText.setOnEditorActionListener(this);
        mSearchEditText.clearFocus();
    }

    public void setListener(OnSearchListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            String query = v.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                onSearch(query);
            }
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete_search_button) {
            onClickDelete();

        }
    }

    private void onClickDelete() {
        clearSearchText();
        Utils.hideSoftKeyboard(mSearchEditText, getContext());
    }

    private void clearSearchText() {
        if (mSearchEditText != null) {
            mSearchEditText.setText("");
        }
    }

    private void onSearch(String expression) {
        if (mListener != null) {
            mListener.onSearch(expression);
        }
    }

    public interface OnSearchListener {

        void onSearch(String expression);
    }
}
