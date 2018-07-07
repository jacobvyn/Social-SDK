package com.testapp.ecommerce.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.testapp.ecommerce.R;

/**
 * Created by vynnykiakiv on 6/7/17.
 */

public class StarsRateDialogFragment extends DialogFragment implements View.OnClickListener {
    private OnStarsRateDialogFragmentListener mListener;
    private static final int INITIAL_STARS_COUNT = 4;
    private float mStarsCount = INITIAL_STARS_COUNT;

    public interface OnStarsRateDialogFragmentListener {
        void onRateChosen(float stars);
    }

    public static StarsRateDialogFragment newInstance(Bundle bundle) {
        StarsRateDialogFragment fragment = new StarsRateDialogFragment();
        fragment.setArguments(bundle);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stars_rate_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.rate_dialog_ok_button).setOnClickListener(this);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rate_dialog_stars_set_layout);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mStarsCount = rating;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rate_dialog_ok_button: {
                onRateChosen(mStarsCount);
                dismiss();
                break;
            }
        }
    }

    public void setListener(OnStarsRateDialogFragmentListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    private void onRateChosen(float stars) {
        if (mListener != null) {
            mListener.onRateChosen(stars);
        }
    }
}
