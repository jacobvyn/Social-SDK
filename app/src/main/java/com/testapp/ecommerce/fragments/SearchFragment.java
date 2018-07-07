package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.testapp.ecommerce.R;
import com.testapp.ecommerce.common.Utils;

/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class SearchFragment extends Fragment implements View.OnClickListener,
        TextWatcher {
    private static final int THRESHOLD_SYMBOL_COUNT = 2;
    private OnSearchFragmentListener mOnSearchFragmentListener;
    private View mResultsLayout;
    private EditText mSearchEditText;
    private Context mContext;
    private TextView mSearchResultTextView;


    public interface OnSearchFragmentListener {
        void onSearch(String expression);

        void onDelete();
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnSearchFragmentListener) {
            mOnSearchFragmentListener = (OnSearchFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchResultTextView = (TextView) view.findViewById(R.id.search_results_textview);
        mSearchEditText = (EditText) view.findViewById(R.id.search_edittext);
        mResultsLayout = view.findViewById(R.id.results_layout);

        view.findViewById(R.id.delete_search_button).setOnClickListener(this);

        mSearchEditText.addTextChangedListener(this);
        mSearchEditText.clearFocus();
    }

    @Override
    public void onDetach() {
        mOnSearchFragmentListener = null;
        super.onDetach();
    }

    private void onSearch(String expression) {
        if (mOnSearchFragmentListener != null) {
            mOnSearchFragmentListener.onSearch(expression);
        }
    }

    private void onDelete() {
        if (mOnSearchFragmentListener != null) {
            mOnSearchFragmentListener.onDelete();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_search_button: {
                onClickDelete();
                break;
            }
        }
    }

    private void onClickDelete() {
        clearSearchText();
        onDelete();
        resultsLayoutVisibility(false);
        Utils.hideSoftKeyboard(mSearchEditText, mContext);
    }

    private void clearSearchText() {
        if (mSearchEditText != null) {
            mSearchEditText.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String expression = charSequence.toString().toLowerCase();
        if (expression.length() >= THRESHOLD_SYMBOL_COUNT) {
            onSearch(expression);
            resultsLayoutVisibility(true);
        } else if (expression.length() == 0) {
            resultsLayoutVisibility(false);
            onDelete();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public void resultsLayoutVisibility(boolean visibility) {
        if (mResultsLayout != null) {
            if (visibility) {
                mResultsLayout.setVisibility(View.VISIBLE);
            } else {
                mResultsLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setResultsCount(int count) {
        if (mSearchResultTextView != null) {
            mSearchResultTextView.setText(String.valueOf(count));
        }
    }
}
