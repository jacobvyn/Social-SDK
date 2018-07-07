package com.testapp.ecommerce.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.ecommerce.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private final ArrayList<Product> mProductList = new ArrayList<>();
    private OnProductsAdapterListener mListener;


    public interface OnProductsAdapterListener {
        void onItemSelected(Product product);
    }

    public ProductsAdapter(ArrayList<Product> list, OnProductsAdapterListener listener) {
        if (list != null && list.size() > 0) {
            mProductList.addAll(list);
        }
        if (listener != null) {
            mListener = listener;
        }
    }

    public void setList(ArrayList<Product> list) {
        if (list != null) {
            mProductList.clear();
            mProductList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Product product = mProductList.get(position);
        viewHolder.icon.setImageResource(product.getIconResource());
        viewHolder.desc.setText(product.getDescription());
        viewHolder.brand.setText(product.getBrand());
        viewHolder.price.setText(product.getPrice());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected(product);
            }
        });
        if (isLastPosition(position)) {
            viewHolder.divider.setVisibility(View.GONE);
        }
    }

    private boolean isLastPosition(int position) {
        return position == mProductList.size() - 1;
    }

    @Override
    public int getItemCount() {
        if (mProductList != null) {
            return mProductList.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private ImageView icon;
        private TextView desc;
        private TextView price;
        private TextView brand;
        private View divider;

        private ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.product_relative_layout);
            icon = (ImageView) view.findViewById(R.id.product_icon);
            desc = (TextView) view.findViewById(R.id.product_description);
            price = (TextView) view.findViewById(R.id.product_price);
            brand = (TextView) view.findViewById(R.id.product_brand);
            divider = view.findViewById(R.id.divider);
        }
    }

    private void onItemSelected(Product product) {
        if (mListener != null) {
            mListener.onItemSelected(product);
        }
    }
}
