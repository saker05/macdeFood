package com.grocery.hr.lalajikidukan.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.service.CartService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul on 16/5/17.
 */

public class OrderPlacedFragment  extends Fragment  {

    public static final String TAG = OrderPlacedFragment.class.getSimpleName();

    public static OrderPlacedFragment newInstance() {
        return new OrderPlacedFragment();
    }

    private Utils mUtils;
    private MainActivity mActivity;
    private Handler mHandler;
    private CartManager cartManager;


    @BindView(R.id.ll_root)
    LinearLayout mRootWidget;

    @BindView(R.id.text_message)
    TextView message;

    @BindView(R.id.ll_spinner)
    LinearLayout spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mActivity=(MainActivity)getActivity();
        mHandler=new Handler();
        cartManager=CartManager.getInstance(getContext());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            menu.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //message.setVisibility(View.GONE);
        mActivity.showCart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_order_placed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mActivity.hideCart();
        message.setVisibility(View.GONE);
        setUpViews();
    }


    private void setUpViews(){
        new PlaceOrder().execute();
    }

    class PlaceOrder extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.postToServer(AppConstants.Url.BASE_URL
                        + AppConstants.Url.PLACE_ORDERS, mUtils.getUerPasswordMap(getContext()),"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "Place Order::onPostExecute(): result is: " + result);

            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    cartManager.deleteAllCartItems();
                   // message.setVisibility(View.VISIBLE);
                    mUtils.showMessage(
                            mActivity,
                            "Order",
                            "Your order has been successfully placed",
                            "OK", null
                    );
                    mActivity.clearBackStack();
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
            spinner.setVisibility(View.GONE);
        }
    }

    public void showSnackbar(String message) {
        try {
            Snackbar.make(mRootWidget, message,
                    Snackbar.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
