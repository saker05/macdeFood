package com.grocery.hr.lalajikidukan.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.service.CartService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PaymentOptionFragment extends Fragment {
    private Handler mHandler;
    private MainActivity mActivity;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private Utils mUtils;
    private CartManager cartManager;
    private String cartModelJson;
    private Gson gson;
    private int defaultAddressId;
    private List<CartModel> cartItems;
    private CartService cartService;
    private ShippingModel shippingDetail;

    public static final String TAG = PaymentOptionFragment.class.getSimpleName();

    @BindView(R.id.rl_root)
    RelativeLayout mRootWidget;

    @BindView(R.id.text_total_price_top)
    TextView totalPriceTop;

    @BindView(R.id.text_order_price)
    TextView orderPrice;

    @BindView(R.id.text_delivery_price)
    TextView mDeliveryCharge;

    @BindView(R.id.text_total_price)
    TextView totalSumPrice;

    @BindView(R.id.ll_place_order)
    LinearLayout placeOrder;

    @BindView(R.id.ll_spinner)
    LinearLayout spinner;



    private PaymentOptionFragment(int defaultAddressId){
        this.defaultAddressId=defaultAddressId;
    }


    public static PaymentOptionFragment newInstance(int defaultAddressId) {
        return new PaymentOptionFragment(defaultAddressId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        cartManager=CartManager.getInstance(getContext());
        gson = new Gson();
        cartService = CartService.getInstance(getContext());
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_option, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.showCart();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity.hideCart();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        setUpToolbar();
        setupViews();
    }


    public void setUpToolbar() {
        mActivity.setSupportActionBar(mToolbar);
        mActivity.setTitle("Payment Mode");
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }


    public void setupViews() {
        placeOrder.setVisibility(View.GONE);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new UpdateDefaultAddress().execute();
            }
        });
    }


    @OnClick(R.id.ll_place_order)
    public void placeOrder() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContentMain, OrderPlacedFragment
                                .newInstance(),
                        OrderPlacedFragment.TAG).addToBackStack(null)
                .commit();
    }


    private void refreshChargeDetail() {
        int cartToalPrice = cartService.getCartTotalPrice(cartItems);
        orderPrice.setText(String.valueOf(cartToalPrice));

        shippingDetail = JsonParserUtils.shippingParser(AppSharedPreference.getString(getContext(), AppConstants.User.SHIPPING_DETAIL));
        int shippingCharge = cartService.getShippingCharge(shippingDetail, cartToalPrice);
        if (shippingCharge != 0) {
            mDeliveryCharge.setText(String.valueOf(shippingCharge));
        } else {
            mDeliveryCharge.setText("Free");
        }
        totalPriceTop.setText(String.valueOf(cartToalPrice+shippingCharge));
        totalSumPrice.setText(String.valueOf(cartToalPrice+shippingCharge));

        placeOrder.setVisibility(View.VISIBLE);
    }


    class PostCart extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            List<CartDO> cartDOs = cartManager.getCartItems();
            List<CartModel> cartModelList = mUtils.convertCartDosTOCartModel(cartDOs);
            if (cartModelList != null && !cartModelList.isEmpty()) {
                cartModelJson = gson.toJson(cartModelList);
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            if (cartModelJson == null || cartModelJson.isEmpty()) {
                return null;
            } else if (!mUtils.isUserLoggedIn(getContext())) {
                try {
                    return mUtils.postToServer(AppConstants.Url.BASE_URL +
                            AppConstants.Url.CART_PRODUCT_INFO, null, cartModelJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            } else {
                try {
                    return mUtils.postToServer(AppConstants.Url.BASE_URL + AppConstants.Url.ADD_CART,
                            mUtils.getUerPasswordMap(getContext()), cartModelJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCart::onPostExecute(): result is: " + result);
            if ((cartModelJson == null || cartModelJson.isEmpty()) ||
                    (result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    cartItems = JsonParserUtils.cartParser(result);
                    refreshChargeDetail();
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
            spinner.setVisibility(View.GONE);
        }
    }


    class UpdateDefaultAddress extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = AppConstants.Url.BASE_URL + AppConstants.Url.UPDATE_DEFAULT + "?id=" +defaultAddressId ;
                return mUtils.putToServer(url, mUtils.getUerPasswordMap(getContext()), String.valueOf(defaultAddressId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "Update default::onPostExecute(): result is: " + result);

            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " +
                            getString(R.string.complaint_to_admin));
                } else {
                    new PostCart().execute();
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
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





