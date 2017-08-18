package com.grocery.hr.lalajikidukan.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grocery.hr.lalajikidukan.dao.CategoryDAO;
import com.grocery.hr.lalajikidukan.dao.ProductDAO;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipul on 18/8/17.
 */

public class ProductManager {

    private  static ProductManager instance;

    private static ProductDAO productDAO;

    private ProductManager(){
    }

    public static ProductManager getInstance(Context context){
        if(instance==null){
            productDAO=ProductDAO.getInstance(context);
            instance=new ProductManager();
        }
        return instance;
    }

    public void deleteAllHighLightedProducts(){
        productDAO.deleteAllProducts();
    }


    public List<ProductModel> getAllHighLightedProducts(){
        List<ProductModel> prouductModelList=new ArrayList<>();
        Cursor cursor= productDAO.getAllProducts();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            ProductModel productModel=new ProductModel();
            productModel.setImageUrl(cursor.getString(1));
            productModel.setCategoryId(cursor.getInt(0));

            prouductModelList.add(productModel);

            cursor.moveToNext();
        }
        return prouductModelList;
    }

    public void insertHighLightedProduct(List<ProductModel> productModelList){
        for(ProductModel productModel:productModelList){
            productDAO.insertProduct(productModel);
        }
    }

}
