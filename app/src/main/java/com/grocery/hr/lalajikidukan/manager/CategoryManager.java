package com.grocery.hr.lalajikidukan.manager;

import android.content.Context;
import android.database.Cursor;

import com.grocery.hr.lalajikidukan.dao.CartDAO;
import com.grocery.hr.lalajikidukan.dao.CategoryDAO;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipul on 18/8/17.
 */

public class CategoryManager {

    private  static CategoryManager instance;

    private static CategoryDAO categoryDAO;

    private CategoryManager(){
    }

    public static CategoryManager getInstance(Context context){
        if(instance==null){
            categoryDAO=CategoryDAO.getInstance(context);
            instance=new CategoryManager();
        }
        return instance;
    }

    public void deleteAllCategories(){
        categoryDAO.deleteAllCategories();
    }

    public List<CategoryModel> getAllCategories(){
        List<CategoryModel> categoryModelList=new ArrayList<>();
        Cursor cursor= categoryDAO.getAllCategories();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            CategoryModel categoryModel=new CategoryModel();
            categoryModel.setDescription(cursor.getString(4));
            categoryModel.setImageUrl(cursor.getString(3));
            categoryModel.setId(cursor.getInt(0));
            categoryModel.setName(cursor.getString(1));
            categoryModel.setStatus(cursor.getString(2));

            categoryModelList.add(categoryModel);

            cursor.moveToNext();
        }
        return categoryModelList;
    }

    public void insertCategories(List<CategoryModel> categoryModelList){
        for(CategoryModel categoryModel:categoryModelList){
            categoryDAO.insertCategory(categoryModel);
        }
    }

}
