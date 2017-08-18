package com.grocery.hr.lalajikidukan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import com.grocery.hr.lalajikidukan.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by vipul on 18/8/17.
 */

public class CategoryDAO {

    public static final String TAG = "CategoryDAO";

    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String IMAGE_URL = "image_url";
    public static final String CATEGORY_ID = "id";
    public static final String DESCRIPTION = "description";

    public static final String TABLE_CATEGORY = "category";

    public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + TABLE_CATEGORY
            + "("
            + CATEGORY_ID
            + " INTEGER PRIMARY KEY, "
            + NAME
            + " TEXT,"
            + STATUS
            + " TEXT, "
            + IMAGE_URL
            + " TEXT, "
            + DESCRIPTION
            + " TEXT "
            + ")";


    private static CategoryDAO instance;

    private static BaseDBHelper baseDBHelper;

    private CategoryDAO() {
    }

    public static CategoryDAO getInstance(Context context) {
        if (instance == null) {
            baseDBHelper = BaseDBHelper.getInstance(context);
            instance = new CategoryDAO();
        }
        return instance;
    }

    public void insertCategory(CategoryModel categoryModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_ID, categoryModel.getId());
        contentValues.put(NAME, categoryModel.getName());
        contentValues.put(STATUS, categoryModel.getStatus());
        contentValues.put(IMAGE_URL, categoryModel.getImageUrl());
        contentValues.put(DESCRIPTION, categoryModel.getDescription());
        baseDBHelper.insert(contentValues, TABLE_CATEGORY);
    }

    public void deleteAllCategories() {
        baseDBHelper.deleteTableItems(TABLE_CATEGORY);
    }

    public Cursor getAllCategories() {
        String sql = "select * from " + TABLE_CATEGORY;
        return baseDBHelper.executeRawQuery(sql, null);
    }


}
