package com.grocery.hr.lalajikidukan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grocery.hr.lalajikidukan.entity.CartDO;

import java.util.List;

/**
 * Created by vipul on 24/3/17.
 */

public class CartDAO {

    public static final String TAG = "CartDAO";

    public static final String UPC = "upc";
    public static final String NO_OF_UNIT = "no_of_unit";
    public static final String KEY_ID = "id";

    public static final String TABLE_USER_CART = "user_cart";

    public static final String CREATE_USER_CART_TABLE = "CREATE TABLE "
            + TABLE_USER_CART
            + "("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UPC
            + " TEXT,"
            + NO_OF_UNIT
            + " INTEGER DEFAULT 1"
            + ")";


    private static CartDAO instance;

    private static  BaseDBHelper baseDBHelper;

    private CartDAO() {
    }

    public static CartDAO getInstance(Context context) {
        if(instance==null){
            baseDBHelper=BaseDBHelper.getInstance(context);
            instance=new CartDAO();
        }
        return instance;
    }

  /*  public void insertCartItems(List<CartDO> cartDOs) {
        for (CartDO cartDO : cartDOs) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(UPC, cartDO.getUpc());
            contentValues.put(NO_OF_UNIT, cartDO.getNoOfUnits());

            baseDBHelper.insert(contentValues, TABLE_USER_CART);
        }
    }*/

    public void insertCartItem(CartDO cartDO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UPC, cartDO.getUpc());
        contentValues.put(NO_OF_UNIT, cartDO.getNoOfUnits());

        baseDBHelper.insert(contentValues, TABLE_USER_CART);
    }

    public void deleteAllCartItems() {
        baseDBHelper.deleteTableItems(TABLE_USER_CART);
    }

    public void deleteCartItem(CartDO cartDO) {
        baseDBHelper.delete(TABLE_USER_CART, UPC + " ?",
                new String[]{cartDO.getUpc()});
    }

    public void updateCartItem(CartDO cartDO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UPC, cartDO.getUpc());
        contentValues.put(NO_OF_UNIT, cartDO.getNoOfUnits());
        baseDBHelper.update(TABLE_USER_CART, contentValues, UPC + "=?",
                new String[]{cartDO.getUpc()});
    }

    public Cursor getCartItems(){
        String sql="select " +UPC+","+NO_OF_UNIT +" from "+TABLE_USER_CART;
        return baseDBHelper.executeRawQuery(sql,null);
    }

    public Cursor getCartItem(String upc){
        String sql="select " +UPC+","+NO_OF_UNIT +" from "+TABLE_USER_CART+" where "+UPC+"='"+upc+"'";
        return baseDBHelper.executeRawQuery(sql,null);
    }

}
