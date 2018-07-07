package com.testapp.ecommerce.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.ecommerce.R;
import com.testapp.ecommerce.common.Constants;
import com.testapp.ecommerce.model.DataSource;
import com.testapp.ecommerce.MainActivity;
import com.testapp.ecommerce.ProductActivity;
import com.testapp.ecommerce.model.ProductsAdapter;
import com.testapp.ecommerce.model.Product;

import java.util.ArrayList;

/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class ProductsListFragment extends Fragment {

    private Context mContext;
    private ProductsAdapter mProductsAdapter;

    public static ProductsListFragment newInstance(String type) {
        ProductsListFragment fragment = new ProductsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARG_PRODUCT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Product> productList = getList();

        mProductsAdapter = new ProductsAdapter(productList, initOnProductsAdapterListener());

        RecyclerView mGoodsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mGoodsRecyclerView.setAdapter(mProductsAdapter);
        updateResultsCount(productList.size());
    }

    private ProductsAdapter.OnProductsAdapterListener initOnProductsAdapterListener() {
        return new ProductsAdapter.OnProductsAdapterListener() {
            @Override
            public void onItemSelected(Product product) {
                if (product != null) {
                    launchProductViewActivity(product);
                }
            }
        };
    }

    private void launchProductViewActivity(Product product) {
        if (product != null) {
            Intent intent = new Intent(mContext, ProductActivity.class);
            intent.putExtra(Constants.ARG_PRODUCT, product.toJson().toString());
            startActivity(intent);
        }
    }

    public void setProductsList(String expression) {
        ArrayList<Product> productList = DataSource.getProductList(expression);
        if (productList != null && mContext instanceof MainActivity) {
            mProductsAdapter.setList(productList);
            updateResultsCount(productList.size());
        }
    }

    public void updateResultsCount(int count) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.search_root);
        if (fragment != null && fragment instanceof SearchFragment) {
            ((SearchFragment) fragment).setResultsCount(count);
        }
    }

    public ArrayList<Product> getList() {
        if (getArguments() != null) {
            String productType = getArguments().getString(Constants.ARG_PRODUCT_TYPE);
            if (!TextUtils.isEmpty(productType)) {
                return DataSource.getProductList(productType);
            }
        }
        return DataSource.getProductList();
    }
}
