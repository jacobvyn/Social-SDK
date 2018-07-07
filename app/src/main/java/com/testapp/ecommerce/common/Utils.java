package com.testapp.ecommerce.common;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testapp.ecommerce.R;
import com.testapp.ecommerce.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vynnykiakiv on 5/30/17.
 */

public class Utils {
    public static int getIcon(String icon) {
        switch (icon) {
            case Constants.PHONE_BLU: {
                return R.drawable.blu;
            }
            case Constants.PHONE_SAMSUNG: {
                return R.drawable.samsung;
            }
            case Constants.PHONE_ZTE: {
                return R.drawable.zte;
            }
            case Constants.PHONE_IPHONE: {
                return R.drawable.iphone;
            }
            case Constants.PHONE_MOTOROLA: {
                return R.drawable.moto;
            }

            case Constants.DRESS_GRACE: {
                return R.drawable.grace;
            }
            case Constants.DRESS_MIYANG: {
                return R.drawable.miyang;
            }
            case Constants.DRESS_PAKULA: {
                return R.drawable.pakula_dress;
            }
            case Constants.DRESS_POSESHE: {
                return R.drawable.poseshe;
            }
            case Constants.DRESS_TINYHI: {
                return R.drawable.tinyhi;
            }

            case Constants.PERFUME_PAKULA: {
                return R.drawable.pakula;
            }
            case Constants.PERFUME_BETSEY: {
                return R.drawable.betsey;
            }
            case Constants.PERFUME_BURBERRY: {
                return R.drawable.burberry;
            }
            case Constants.PERFUME_LACOSTE: {
                return R.drawable.lacoste;
            }
            case Constants.PERFUME_TAYLOR: {
                return R.drawable.taylor;
            }

            default: {
                return 0;
            }
        }
    }


    public static ArrayList<Product> readFromFile(File file) {
        ArrayList<Product> productArrayList = new ArrayList<>();
        try {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                JsonFactory jsonFactory = new JsonFactory();
                JsonParser jsonParser = jsonFactory.createParser(fileInputStream);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                jsonParser.setCodec(objectMapper);
                jsonParser.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                jsonParser.nextToken();

                while (jsonParser.hasCurrentToken()) {
                    Product[] goods = jsonParser.readValueAs(Product[].class);
                    jsonParser.nextToken();
                    productArrayList.addAll(Arrays.asList(goods));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productArrayList;
    }

    public static boolean saveFile(File file, String message, boolean append) {
        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            try (FileWriter fileWriter = new FileWriter(file, append)) {
                fileWriter.write(message);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public static File createPublicFile(String fileName) {
        File file = null;
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            file = new File(path, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static JSONObject createJsonObject(String key, String value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, value);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hideSoftKeyboard(View view, Context context) {
        if (view != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static ArrayList<Product> findSearchedGoods(String searchGoods, ArrayList<Product> oldList) {
        ArrayList<Product> list = new ArrayList<>();
        if (oldList != null) {
            for (Product product : oldList) {
                if (product.getType().contains(searchGoods)) {
                    list.add(product);
                }
            }
            return list;
        }
        return list;
    }
}
