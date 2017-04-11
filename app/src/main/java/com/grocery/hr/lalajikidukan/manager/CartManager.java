package com.grocery.hr.lalajikidukan.manager;

import android.content.Context;
import android.database.Cursor;

import com.grocery.hr.lalajikidukan.dao.CartDAO;
import com.grocery.hr.lalajikidukan.entity.CartDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipul on 25/3/17.
 */

public class CartManager {

    private  static CartManager instance;

    private static CartDAO cartDAO;

    private CartManager(){
    }

    public static CartManager getInstance(Context context){
        if(instance==null){
            cartDAO=CartDAO.getInstance(context);
            instance=new CartManager();
        }
        return instance;
    }

    public int noOfItemInCart(){
        Cursor cursor=cartDAO.getCartItems();
        return cursor.getCount();
    }

    public void insertByOne(String upc) {
        CartDO cartDO=getCartDOfromCursor(cartDAO.getCartItem(upc));
        if(cartDO==null){
            cartDO=new CartDO();
            cartDO.setUpc(upc);
            cartDO.setNoOfUnits(1);
            cartDAO.insertCartItem(cartDO);
        }else{
            cartDO.setNoOfUnits(cartDO.getNoOfUnits()+1);
            cartDAO.updateCartItem(cartDO);
        }
    }

    public void removeByOne(String upc){
        CartDO cartDO=getCartDOfromCursor(cartDAO.getCartItem(upc));
        if(cartDO!=null){
            int noOfItem=cartDO.getNoOfUnits();
            if(noOfItem<=1){
                cartDAO.deleteCartItem(cartDO);
            }else{
                cartDO.setNoOfUnits(cartDO.getNoOfUnits()-1);
                cartDAO.updateCartItem(cartDO);
            }
        }
    }

    public void deleteAllCartItems(){
        cartDAO.deleteAllCartItems();
    }

    public List<CartDO> getCartItems(){
        List<CartDO> cartDOs=new ArrayList<>();
        Cursor cursor= cartDAO.getCartItems();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            CartDO cartDO=new CartDO();
            cartDO.setNoOfUnits(cursor.getInt(1));
            cartDO.setUpc(cursor.getString(0));
            cartDOs.add(cartDO);
            cursor.moveToNext();
        }
        return cartDOs;
    }

    private CartDO getCartDOfromCursor(Cursor cursor){
        CartDO cartDO=null;
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            cartDO=new CartDO();
            cartDO.setNoOfUnits(cursor.getInt(1));
            cartDO.setUpc(cursor.getString(0));
            break;
        }
        return cartDO;
    }


}
