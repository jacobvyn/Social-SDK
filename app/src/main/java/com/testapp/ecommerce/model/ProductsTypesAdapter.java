package com.testapp.ecommerce.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testapp.ecommerce.R;

import java.util.ArrayList;

/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class ProductsTypesAdapter extends RecyclerView.Adapter<ProductsTypesAdapter.ViewHolder> {
    private OnProductsTypeAdapterListener mCallback;
    private final ArrayList<String> mProductTypesList = new ArrayList<>();

    public interface OnProductsTypeAdapterListener {
        void onItemSelected(String productType);
    }

    public ProductsTypesAdapter(OnProductsTypeAdapterListener listener) {
        if (listener != null) {
            mCallback = listener;
        }
        mProductTypesList.addAll(DataSource.getProductTypes());
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.type_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String type = mProductTypesList.get(position);
        holder.productType.setText(type);
        holder.productType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected(type.toLowerCase());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductTypesList.size();
    }

    private void onItemSelected(String type) {
        if (mCallback != null) {
            mCallback.onItemSelected(type);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productType;

        public ViewHolder(View view) {
            super(view);
            productType = (TextView) view.findViewById(R.id.product_type_textview);
        }
    }
}
