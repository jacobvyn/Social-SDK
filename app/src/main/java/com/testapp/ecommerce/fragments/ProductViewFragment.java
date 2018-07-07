package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.ecommerce.R;
import com.testapp.ecommerce.common.Constants;
import com.testapp.ecommerce.model.Product;

import static com.testapp.ecommerce.common.Constants.ARG_PRODUCT;

public class ProductViewFragment extends Fragment implements View.OnClickListener {

    private static final String SIZE_DRESS = "XS";
    private static final String SIZE_PERFUME = "50 ml";
    private static final String STARS_RATE_DIALOG = "STARS_RATE_DIALOG";

    private OnProductViewFragmentListener mOnProductViewFragmentListener;
    private Product mProduct;


    public interface OnProductViewFragmentListener {
        void onBuyClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductViewFragmentListener) {
            mOnProductViewFragmentListener = (OnProductViewFragmentListener) context;
        }
    }

    public static ProductViewFragment newInstance(Product product) {
        ProductViewFragment fragment = new ProductViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.product_view_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrieveProduct();
        setViews(view);
    }

    private void retrieveProduct() {
        if (getArguments() != null) {
            Object object = getArguments().getSerializable(ARG_PRODUCT);
            if (object != null && object instanceof Product) {
                mProduct = (Product) object;
            }
        }
    }

    @Override
    public void onDetach() {
        mOnProductViewFragmentListener = null;
        super.onDetach();
    }

    private void setViews(View view) {
        if (mProduct == null) {
            return;
        }
        ((ImageView) view.findViewById(R.id.product_icon)).setImageResource(mProduct.getIconResource());
        ((TextView) view.findViewById(R.id.product_brand)).setText(mProduct.getBrand());
        ((TextView) view.findViewById(R.id.product_price)).setText(mProduct.getPrice());
        ((TextView) view.findViewById(R.id.product_description)).setText(mProduct.getDescription());

        TextView sizeTextView = (TextView) view.findViewById(R.id.size_textview);
        TextView colorTextView = (TextView) view.findViewById(R.id.color_textview);
        TextView colorValueTextView = (TextView) view.findViewById(R.id.color_value_textview);

        view.findViewById(R.id.buy_now_layout).setOnClickListener(this);
        view.findViewById(R.id.product_stars).setOnClickListener(this);

        changeColorFieldForPerfume(colorTextView, colorValueTextView);
        setSize(sizeTextView);
    }

    private void showStarsRateDialog() {
        StarsRateDialogFragment starsRateDialogFragment = StarsRateDialogFragment.newInstance(null);
        starsRateDialogFragment.show(getActivity().getSupportFragmentManager(), STARS_RATE_DIALOG);
        starsRateDialogFragment.setListener(initStarsRateDialogListener());
    }

    private StarsRateDialogFragment.OnStarsRateDialogFragmentListener initStarsRateDialogListener() {
        return new StarsRateDialogFragment.OnStarsRateDialogFragmentListener() {
            @Override
            public void onRateChosen(float stars) {
                Toast.makeText(getActivity(), "" + stars, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void changeColorFieldForPerfume(TextView colorTextView, TextView colorValueTextView) {
        if (mProduct != null) {
            if (TextUtils.equals(mProduct.getType(), Constants.TYPE_PERFUME)) {
                colorTextView.setText(R.string.giftset);
                colorValueTextView.setText(R.string.available);
            }
        }
    }

    private void setSize(TextView sizeTextView) {
        if (mProduct != null) {
            if (TextUtils.equals(mProduct.getType(), Constants.TYPE_DRESS)) {
                sizeTextView.setText(SIZE_DRESS);
            } else if (TextUtils.equals(mProduct.getType(), Constants.TYPE_PERFUME)) {
                sizeTextView.setText(SIZE_PERFUME);
            }
        }
    }

    private void onBuyClicked() {
        if (mOnProductViewFragmentListener != null) {
            mOnProductViewFragmentListener.onBuyClicked();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_now_layout: {
                onBuyClicked();
                break;
            }
            case R.id.product_stars: {
                showStarsRateDialog();
                break;
            }
        }
    }
}
