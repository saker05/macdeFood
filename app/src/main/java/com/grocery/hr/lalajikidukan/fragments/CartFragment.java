package com.grocery.hr.lalajikidukan.fragments;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.Test.CartTest;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.service.CartService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends Fragment {

    public static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.clRootCart)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.tbToolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvCart)
    SuperRecyclerView mCartList;

    @BindView(R.id.price)
    TextView mCartTotal;

    @BindView(R.id.dcfree)
    TextView mDeliveryCharge;

    @BindView(R.id.checkoutButtonLayout)
    LinearLayout checkoutButtonLayout;

    @BindView(R.id.shippingDetailLayout)
    LinearLayout shippingDetailLayout;

    private CartAdapter mAdapter;
    private MainActivity mActivity;
    private Handler mHandler;
    private Gson gson;
    private Utils mUtils;
    private List<CartModel> cartItems;
    private ShippingModel shippingDetail;
    private CartManager cartManager;
    private String cartModelJson;
    private CartService cartService;



    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        gson = new Gson();
        cartManager = CartManager.getInstance(getContext());
        mAdapter = new CartAdapter();
        cartService = CartService.getInstance(getContext());

        AppSharedPreference.putString(getContext(), AppConstants.User.MOBILE_NO, "9729012780");
        AppSharedPreference.putString(getContext(), AppConstants.User.PASSWORD, "vipul");
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity.hideCart();
        setUpViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.showCart();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUpViews() {
        mActivity.setSupportActionBar(mToolbar);
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

//        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mActivity.getDrawerLayout(), mToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mActivity.getDrawerLayout().addDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();

        mActivity.setTitle(getString(R.string.action_cart));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCartList.setLayoutManager(linearLayoutManager);
        mCartList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseGetCart();
            }
        });
        baseGetCart();
    }


    public void baseGetCart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetCart().execute();
                new GetShippingDetail().execute();
            }
        });


    }


    class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        @Override
        public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CartViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_cart, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(CartViewHolder holder, int position) {
            CartModel item = cartItems.get(position);
           /* Picasso.with(mActivity)
                    .load(R.drawable.placeholder)
                    .into(holder.getLogo());*/
            holder.getName().setText(item.getName());
            // holder.getDeliveryTime().setText(item.getDeliveryTime());
            holder.getQtyRate().setText(String.valueOf(
                    item.getUnitAmount()
            ));
            holder.getMunits().setText(String.valueOf(
                    item.getNoOfUnits()
            ));

            if (item.getUnitQuantityInGm() == 0) {
                holder.getQuantity().setText("1 unit");
            } else {
                holder.getQuantity().setText(String.valueOf(item.getUnitQuantityInGm()) + "gm");
            }
        }

        @Override
        public int getItemCount() {
            if (cartItems == null) {
                return 0;
            } else {
                return cartItems.size();
            }

        }

    }



    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


/*
        @BindView(R.id.imageView)
       AppCompatImageView mLogo;*/

        @BindView(R.id.content)
        AppCompatTextView mcontent;

        @BindView(R.id.quantity)
        AppCompatTextView mquantity;

        @BindView(R.id.rupee)
        AppCompatTextView mQtyRate;

        @BindView(R.id.Noofunits)
        AppCompatTextView munits;

        @BindView(R.id.cart_plus)
        ImageView mPlus;

        @BindView(R.id.cart_minus)
        ImageView mMinus;

        //   @BindView(R.id.tvCartTotal)
        // AppCompatTextView mTotal;

        public CartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   mHeader.setVisibility(View.GONE);
            // itemView.setOnClickListener(this);
        }

        @OnClick(R.id.cart_plus)
        public void onPlusClick() {
            CartModel item = cartItems.get(getAdapterPosition());
            cartManager.insertByOne(item.getUpc());
            item.setNoOfUnits(item.getNoOfUnits() + 1);
            refreshBottomDetail();

            mAdapter.notifyDataSetChanged();

        }

        @OnClick(R.id.cart_minus)
        public void onMinusClick() {
            CartModel item = cartItems.get(getAdapterPosition());
            cartManager.removeByOne(item.getUpc());
            if (item.getNoOfUnits() == 1) {
                cartItems.remove(getAdapterPosition());
            } else {
                item.setNoOfUnits(item.getNoOfUnits() - 1);
            }
            refreshBottomDetail();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
           /* CartItem item = mAdapter.getCartItems().get(getAdapterPosition());
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, ProductDetailFragment.newInstance(
                            item.toString()
                    ), ProductDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();*/
        }

       /* public AppCompatImageView getLogo() {
            return mLogo;
        }*/

        public AppCompatTextView getName() {
            return mcontent;
        }

        public AppCompatTextView getQtyRate() {
            return mQtyRate;
        }

        public AppCompatTextView getQuantity() {
            return mquantity;
        }

        public AppCompatTextView getMunits() {
            return munits;
        }

    }


    class GetShippingDetail extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.GET_SHIPPING_DETAIL, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetShippingDetail::onPostExecute(): result is: " + result);
            if (result != null && result.trim().length() != 0) {
                shippingDetail = JsonParserUtils.shippingParser(result);
                refreshBottomDetail();
            }
        }


    }

    class GetCart extends AsyncTask<Void, Void, String> {

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
                    return mUtils.postToServer(AppConstants.Url.BASE_URL + AppConstants.Url.CART_PRODUCT_INFO, null, cartModelJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                try {
                    return mUtils.postToServer(AppConstants.Url.BASE_URL + AppConstants.Url.ADD_CART, mUtils.getUerPasswordMap(getContext()), cartModelJson);
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
            if ((cartModelJson == null || cartModelJson.isEmpty()) || (result != null && result.trim().length() != 0)) {
                cartItems = JsonParserUtils.cartParser(result);
                mCartList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void refreshBottomDetail() {
        if (cartItems == null || cartItems.size() == 0) {
            shippingDetailLayout.setVisibility(View.GONE);
            checkoutButtonLayout.setVisibility(View.GONE);
        } else {
            int cartToalPrice = cartService.getCartTotalPrice(cartItems);
            mCartTotal.setText(String.valueOf(cartToalPrice));

            int deliveryCharge = cartService.getShippingCharge(shippingDetail, cartToalPrice);
            if (deliveryCharge != 0) {
                mDeliveryCharge.setText(String.valueOf(deliveryCharge));
            } else {
                mDeliveryCharge.setText("Free");
            }
        }
    }

}


