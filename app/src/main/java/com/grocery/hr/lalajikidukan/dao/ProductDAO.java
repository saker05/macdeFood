package com.grocery.hr.lalajikidukan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.models.ProductVariantsModel;

import java.util.List;

/**
 * Created by vipul on 18/8/17.
 */

public class ProductDAO {

    public static final String TAG = "ProductDAO";

    public static final String IMAGE_URL = "image_url";
    public static final String CATEGORY_ID = "id";


    public static final String TABLE_PRODUCT = "product";

    public static final String CREATE_PRODUCT_TABLE = "CREATE TABLE "
            + TABLE_PRODUCT
            + "("
            + CATEGORY_ID
            + " INTEGER, "
            + IMAGE_URL
            + " TEXT "
            + ")";


    private static ProductDAO instance;

    private static BaseDBHelper baseDBHelper;

    private ProductDAO() {
    }

    public static ProductDAO getInstance(Context context) {
        if (instance == null) {
            baseDBHelper = BaseDBHelper.getInstance(context);
            instance = new ProductDAO();
        }
        return instance;
    }

    public void insertProduct(ProductModel productModel) {
        for (ProductVariantsModel productVariantsModel : productModel.getProductVariants()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CATEGORY_ID, productModel.getCategoryId());
            contentValues.put(IMAGE_URL, productModel.getImageUrl());
            baseDBHelper.insert(contentValues, TABLE_PRODUCT);
        }
    }

    public void deleteAllProducts() {
        baseDBHelper.deleteTableItems(TABLE_PRODUCT);
    }

    public Cursor getAllProducts() {
        String sql = "select * from " + TABLE_PRODUCT;
        return baseDBHelper.executeRawQuery(sql, null);
    }


}
