package com.testapp.ecommerce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.testapp.ecommerce.common.Constants;
import com.testapp.ecommerce.fragments.ActionBarFragment;
import com.testapp.ecommerce.fragments.ProductViewFragment;
import com.testapp.ecommerce.fragments.ThanksPageFragment;
import com.testapp.ecommerce.model.Product;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener,
        ProductViewFragment.OnProductViewFragmentListener,
        ThanksPageFragment.OnThanksPageFragment {
    private Product mProduct;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        retrieveProduct();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.product_activity_action_bar, ActionBarFragment.newInstance(false));
        fragmentTransaction.add(R.id.root, ProductViewFragment.newInstance(mProduct));
        fragmentTransaction.commit();
    }

    private void retrieveProduct() {
        if (getIntent().getExtras() != null) {
            String goodsAsJsonString = getIntent().getExtras().getString(Constants.ARG_PRODUCT);
            mProduct = Product.fromJson(goodsAsJsonString);
        }
    }


    @Override
    public void onBuyClicked() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.root, ThanksPageFragment.newInstance(mProduct));
        fragmentTransaction.commit();
    }

    @Override
    public void onBackToSearchClicked() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_search_button: {
                finish();
                break;
            }
        }
    }
}
