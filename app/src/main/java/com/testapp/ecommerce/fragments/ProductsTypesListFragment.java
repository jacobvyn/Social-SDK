package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.ecommerce.R;
import com.testapp.ecommerce.model.ProductsTypesAdapter;

/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class ProductsTypesListFragment extends Fragment {
    private Context mContext;
    private OnProductsTypeListFragmentListener mListener;

    public interface OnProductsTypeListFragmentListener {
        void onItemClicked(String type);
    }

    public static ProductsTypesListFragment newInstance() {
        return new ProductsTypesListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnProductsTypeListFragmentListener) {
            mListener = (OnProductsTypeListFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductsTypesAdapter productsTypesAdapter = new ProductsTypesAdapter(initProductTypeListener());
        RecyclerView productTypesRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        productTypesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        productTypesRecyclerView.setAdapter(productsTypesAdapter);
        productTypesRecyclerView.requestFocus();
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private ProductsTypesAdapter.OnProductsTypeAdapterListener initProductTypeListener() {
        return new ProductsTypesAdapter.OnProductsTypeAdapterListener() {
            @Override
            public void onItemSelected(String productType) {
                onItemClicked(productType);
            }
        };
    }

    private void onItemClicked(String type) {
        if (mListener != null) {
            mListener.onItemClicked(type);
        }
    }
}
