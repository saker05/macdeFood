/*
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
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.UserOrderModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OrderHistoryFragment extends Fragment {

    public static final String TAG = OrderHistoryFragment.class.getSimpleName();

    private MainActivity mActivity;
    private Handler mHandler;
    private Utils mUtils;
    private List<UserOrderModel> orders;


    //XML View

    Toolbar mToolbar;

    @BindView(R.id.ll_spinner)
    LinearLayout spinner;

    @BindView(R.id.cl_root)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.srv_order_history)
    SuperRecyclerView recyclerView;

    private OrderHistoryFragment() {
    }


    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
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
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.showCart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!mUtils.isDeviceOnline(getContext())) {
            return inflater.inflate(R.layout.fragment_no_internet_connection, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_order_history, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.hideCart();
        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
            recyclerView.getProgressView().setVisibility(View.GONE);
            mToolbar = (Toolbar) getActivity().findViewById(R.id.cartToolbar);
            setUpToolbar();
            setUpViews();

        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            setUpToolbar();
        }
    }

    public void setUpToolbar() {
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
        mActivity.setTitle(getString(R.string.action_order_history));
    }

    public void setUpViews() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseGetOrders();
            }
        });
        baseGetOrders();
    }




    class OrderAdapter extends RecyclerView.Adapter<CartFragment.CartViewHolder> {
        @Override
        public CartFragment.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CartFragment.CartViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_cart, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(CartFragment.CartViewHolder holder, int position) {
            CartModel item = cartItems.get(position);
           */
/* Picasso.with(mActivity)
                    .load(R.drawable.placeholder)
                    .into(holder.getLogo());*//*

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


    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
*/
/*
        @BindView(R.id.imageView)
       AppCompatImageView mLogo;*//*


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

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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


       */
/* public AppCompatImageView getLogo() {
            return mLogo;
        }*//*


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

        @Override
        public void onClick(View v) {

        }
    }


    public void baseGetOrders() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetOrder().execute();
            }
        });
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
                    cartItems = JsonParserUtils.cartParser(result);
                    mCartList.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
        }
    }

    private void showSnackbar(String message) {
        try {
            Snackbar.make(mRootWidget, message,
                    Snackbar.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
*/
