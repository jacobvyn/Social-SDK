package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.ecommerce.R;


/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class ActionBarFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_SHOW_BUTTON = "ARG_SHOW_BUTTON";

    public boolean mIsShowButton;
    private Listener mListener;

    public static ActionBarFragment newInstance(boolean showButton) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_BUTTON, showButton);

        ActionBarFragment fragment = new ActionBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
        if (getArguments() != null) {
            mIsShowButton = getArguments().getBoolean(ARG_SHOW_BUTTON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.action_bar_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (mIsShowButton) {
            View getSocial = view.findViewById(R.id.action_bar_get_social_image_view);
            getSocial.setOnClickListener(this);
            getSocial.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onGetSocialClicked();
        }
    }

    public interface Listener {
        void onGetSocialClicked();
    }
}
