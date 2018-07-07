package com.testapp.ecommerce.model;

import android.text.TextUtils;

import com.testapp.ecommerce.common.Constants;
import com.testapp.ecommerce.common.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vynnykiakiv on 6/6/17.
 */

public class DataSource {
    private static ArrayList<Product> mProductList;
    private static ArrayList<String> mProductTypes = initProductsTypes();

    private static ArrayList<String> initProductsTypes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Phones");
        list.add("Dresses");
        list.add("Perfumes");
        return list;
    }

    public static ArrayList<Product> getProductList() {
        return mProductList;
    }

    public static ArrayList<Product> getProductList(String expression) {
        ArrayList<Product> searchedList;
        if (!TextUtils.isEmpty(expression) && TextUtils.equals(expression, Constants.ALL_GOODS)) {
            searchedList = new ArrayList<>(mProductList);
        } else {
            searchedList = Utils.findSearchedGoods(expression, mProductList);
        }
        return searchedList;
    }

    public static void init() {
        File file = Utils.createPublicFile(Constants.GOODS_FILE_SOURCE);
        mProductList = Utils.readFromFile(file);
    }

    public static ArrayList<String> getProductTypes() {
        return mProductTypes;
    }

    public static void setProductTypes(ArrayList<String> list) {
        DataSource.mProductTypes = list;
    }
}
