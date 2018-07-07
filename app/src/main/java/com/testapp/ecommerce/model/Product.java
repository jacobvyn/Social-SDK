package com.testapp.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.testapp.ecommerce.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.testapp.ecommerce.common.Constants.FIELD_BRAND;
import static com.testapp.ecommerce.common.Constants.FIELD_DESCRIPTION;
import static com.testapp.ecommerce.common.Constants.FIELD_ID;
import static com.testapp.ecommerce.common.Constants.FIELD_IMAGE;
import static com.testapp.ecommerce.common.Constants.FIELD_PRICE;
import static com.testapp.ecommerce.common.Constants.FIELD_REVIEWS;
import static com.testapp.ecommerce.common.Constants.FIELD_STARS;
import static com.testapp.ecommerce.common.Constants.FIELD_TYPE;

/**
 * Created by vynnykiakiv on 5/30/17.
 */

@JsonRootName(value = "goods")
public class Product implements Serializable {
    @JsonProperty(value = "image")
    private String mIcon;
    @JsonProperty(value = "id")
    private int mId;
    @JsonIgnore
    private int mIconResource;
    @JsonProperty(value = "brand")
    private String mBrand;
    @JsonProperty(value = "price")
    private String mPrice;
    @JsonProperty(value = "stars")
    private double mStars;
    @JsonProperty(value = "reviews")
    private String mReviews;
    @JsonProperty(value = "desc")
    private String mDescription;
    @JsonProperty(value = "type")
    private String mType;

    public Product(int icon) {
        mIconResource = icon;
    }

    public Product() {
    }

    public Product(String icon, int id, String brand, String price, double stars, String reviews, String desc) {
        this.mIcon = icon;
        this.mId = id;
        this.mBrand = brand;
        this.mPrice = price;
        this.mStars = stars;
        this.mReviews = reviews;
        this.mDescription = desc;
    }

    @JsonIgnore
    public String getIcon() {
        return mIcon;
    }

    @JsonIgnore
    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    @JsonIgnore
    public String getBrand() {
        return mBrand;
    }

    @JsonIgnore
    public void setBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    @JsonIgnore
    public String getPrice() {
        return mPrice;
    }

    @JsonIgnore
    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    @JsonIgnore
    public double getStars() {
        return mStars;
    }

    @JsonIgnore
    public void setStars(double mStars) {
        this.mStars = mStars;
    }

    @JsonIgnore
    public String getReviews() {
        return mReviews;
    }

    @JsonIgnore
    public void setReviews(String mReviews) {
        this.mReviews = mReviews;
    }

    @JsonIgnore
    public String getDescription() {
        return mDescription;
    }

    @JsonIgnore
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @JsonIgnore
    public int getId() {
        return mId;
    }

    @JsonIgnore
    public void setId(int mId) {
        this.mId = mId;
    }

    @JsonIgnore
    public int getIconResource() {
        return Utils.getIcon(mIcon);
    }

    @JsonIgnore
    public String getType() {
        return mType;
    }

    @JsonIgnore
    public void setType(String mType) {
        this.mType = mType;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(FIELD_IMAGE, mIcon);
            jsonObject.put(FIELD_DESCRIPTION, mDescription);
            jsonObject.put(FIELD_BRAND, mBrand);
            jsonObject.put(FIELD_PRICE, mPrice);
            jsonObject.put(FIELD_STARS, mStars);
            jsonObject.put(FIELD_REVIEWS, mReviews);
            jsonObject.put(FIELD_ID, mId);
            jsonObject.put(FIELD_TYPE, mType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Product fromJson(String goodsAsJsonString) {
        Product product = new Product();
        try {
            JSONObject jsonObject = new JSONObject(goodsAsJsonString);
            product.setIcon(jsonObject.getString(FIELD_IMAGE));
            product.setDescription(jsonObject.getString(FIELD_DESCRIPTION));
            product.setBrand(jsonObject.getString(FIELD_BRAND));
            product.setPrice(jsonObject.getString(FIELD_PRICE));
            product.setStars(Double.valueOf(jsonObject.getString(FIELD_STARS)));
            product.setReviews(jsonObject.getString(FIELD_REVIEWS));
            product.setId(Integer.valueOf(jsonObject.getString(FIELD_ID)));
            product.setType(jsonObject.getString(FIELD_TYPE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }
}
