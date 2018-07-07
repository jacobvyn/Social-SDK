package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.testapp.ecommerce.R;
import com.testapp.ecommerce.common.Constants;
import com.testapp.ecommerce.model.Product;

public class ThanksPageFragment extends Fragment implements View.OnClickListener {
    private Product mProduct;
    private OnThanksPageFragment mOnThanksPageFragment;

    public interface OnThanksPageFragment {
        void onBackToSearchClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnThanksPageFragment) {
            mOnThanksPageFragment = (OnThanksPageFragment) context;
        }
    }

    public static ThanksPageFragment newInstance(Product product) {
        ThanksPageFragment fragment = new ThanksPageFragment();
        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARG_PRODUCT, product.toJson().toString());
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thank_page_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrieveProduct();
        setViews(view);
    }

    private void retrieveProduct() {
        if (getArguments() != null) {
            String goodsAsJsonString = getArguments().getString(Constants.ARG_PRODUCT);
            mProduct = Product.fromJson(goodsAsJsonString);
        }
    }

    @Override
    public void onDetach() {
        mOnThanksPageFragment = null;
        super.onDetach();
    }

    private void setViews(View view) {
        if (mProduct != null) {
            ((ImageView) view.findViewById(R.id.product_icon)).setImageResource(mProduct.getIconResource());
            view.findViewById(R.id.back_to_search_layout).setOnClickListener(this);
        }
    }

    private void onBackToSearchClicked() {
        if (mOnThanksPageFragment != null) {
            mOnThanksPageFragment.onBackToSearchClicked();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_to_search_layout: {
                onBackToSearchClicked();
                break;
            }
        }
    }
}
