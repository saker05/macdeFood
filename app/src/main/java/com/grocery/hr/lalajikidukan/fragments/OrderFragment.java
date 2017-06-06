/*
package com.grocery.hr.lalajikidukan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;
import com.grocery.hr.lalajikidukan.models.UserOrderModel;
import com.grocery.hr.lalajikidukan.service.CartService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;


public class OrderFragment extends Fragment {

    public static final String TAG = OrderFragment.class.getSimpleName();

    private MainActivity mActivity;
    private Handler mHandler;
    private Utils mUtils;
    private List<UserOrderModel> orders;


    //XML View

    Toolbar mToolbar;

    private OrderFragment() {
    }


    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            menu.clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!mUtils.isDeviceOnline(getContext())) {
            return inflater.inflate(R.layout.fragment_no_internet_connection, container, false);
        } else {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.hideCart();
        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
           */
/* mCartList.getProgressView().setVisibility(View.GONE);
            mToolbar = (Toolbar) getActivity().findViewById(R.id.cartToolbar);
            setUpToolbar();
            setUpViews();*//*

        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            setUpToolbar();
        }
    }




    class GetOrder extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.GET_ORDERS, mUtils.getUerPasswordMap(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetOrder::onPostExecute(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    orders = JsonParserUtils.orderParser(result);
                    */
/*cartItems = JsonParserUtils.cartParser(result);
                    mCartList.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();*//*

                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
        }
    }

    private void showSnackbar(String message) {
      */
/*  try {
            Snackbar.make(mRootWidget, message,
                    Snackbar.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*//*



}
*/
