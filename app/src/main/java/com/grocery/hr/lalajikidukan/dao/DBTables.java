package com.grocery.hr.lalajikidukan.dao;

/**
 * Created by vipul on 24/3/17.
 */

public interface DBTables  {

    String DATABASE_NAME = "groceries.db";

    int DATABASE_VERSION = 1;

    String[] DB_TABLE_NAMES = new String[]{
             CartDAO.TABLE_USER_CART
    };

    String[] DB_SQL_CREATE_TABLE_QUERIES = new String[]{
             CartDAO.CREATE_USER_CART_TABLE
    };
}
