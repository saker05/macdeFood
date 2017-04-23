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
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.service.ProductService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 13/4/17.
 */

public class ProductFragment extends Fragment {

    public static final String TAG = ProductFragment.class.getSimpleName();

    private List<ProductModel> productItems;
    private ProductAdapter mAdapter;
    private MainActivity mActivity;
    private int category;
    private Utils mUtils;
    private Handler mHandler;
    private ProductService productService;
    private CartManager cartManager;

    //XML VIEW

    Toolbar mToolbar;

    @BindView(R.id.clRootproduct)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.rvproduct)
    SuperRecyclerView mProductList;

    @BindView(R.id.linlaHeaderProgress)
    LinearLayout spinner;


    public ProductFragment(int category) {
        this.category = category;
    }

    public static ProductFragment newInstance(int category) {
        return new ProductFragment(category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mAdapter = new ProductAdapter();
        mUtils = Utils.getInstance();
        productService = ProductService.getInstance(getContext());
        mHandler = new Handler();
        cartManager = CartManager.getInstance(getContext());
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
            return inflater.inflate(R.layout.no_internet_connection, container, false);
        } else {
            return inflater.inflate(R.layout.product, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
            mProductList.getProgressView().setVisibility(View.GONE);
            mToolbar = (Toolbar) getActivity().findViewById(R.id.productToolbar);
            setUpToolbar();
            setUpViews();
        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.noInternetConnectionToolbar);
            setUpToolbar();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
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
        mActivity.setTitle("Product");
    }

    public void setUpViews() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProductList.setLayoutManager(linearLayoutManager);
        mProductList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseGetProducts();
            }
        });
        baseGetProducts();
    }

    public void baseGetProducts() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetProducts().execute();
            }
        });
    }


    class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.product_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            ProductModel product = productItems.get(position);

            holder.getMQtyRate().setText(String.valueOf(product.getUnitAmount()));
            if (product.getUnitQuantityInGm() == 0) {
                holder.getMQuantity().setText("1 unit");
            } else {
                holder.getMQuantity().setText(String.valueOf(product.getUnitQuantityInGm()) + "gm");
            }

            if (product.getNoOfItemInCart() == 0) {
                holder.getMMinus().setVisibility(View.GONE);
                holder.getMunits().setVisibility(View.GONE);
            } else {
                holder.getMMinus().setVisibility(View.VISIBLE);
                holder.getMunits().setVisibility(View.VISIBLE);
                holder.getMunits().setText(String.valueOf(product.getNoOfItemInCart()));
            }

            holder.getMContent().setText(product.getName());

        }

        @Override
        public int getItemCount() {
            if (productItems != null) {
                return productItems.size();
            }
            return 0;
        }
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
/*
        @BindView(R.id.imageView)
       AppCompatImageView mLogo;*/

        @BindView(R.id.productContent)
        AppCompatTextView mContent;

        @BindView(R.id.productQuantity)
        AppCompatTextView mQuantity;

        @BindView(R.id.productRupee)
        AppCompatTextView mQtyRate;

        @BindView(R.id.productNoofunits)
        AppCompatTextView mUnits;

        @BindView(R.id.productcart_plus)
        ImageView mPlus;

        @BindView(R.id.productcart_minus)
        ImageView mMinus;

        //   @BindView(R.id.tvCartTotal)
        // AppCompatTextView mTotal;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   mHeader.setVisibility(View.GONE);
            // itemView.setOnClickListener(this);
        }

        @OnClick(R.id.productcart_plus)
        public void onPlusClick() {
            ProductModel product = productItems.get(getAdapterPosition());
            cartManager.insertByOne(product.getUpc());
            product.setNoOfItemInCart(product.getNoOfItemInCart() + 1);
            mActivity.showCart();
            mAdapter.notifyDataSetChanged();
        }

        @OnClick(R.id.productcart_minus)
        public void onMinusClick() {
            ProductModel product = productItems.get(getAdapterPosition());
            cartManager.removeByOne(product.getUpc());
            if (product.getNoOfItemInCart() > 0) {
                product.setNoOfItemInCart(product.getNoOfItemInCart() - 1);
            }
            mActivity.showCart();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            ProductModel product=productItems.get(getAdapterPosition());
            mActivity.getSupportFragmentManager().beginTransaction().replace(
                    R.id.flContentMain,ProductDescriptionFragment.newInstance(product)
            ,ProductDescriptionFragment.TAG).addToBackStack(null).commit();
        }

       /* public AppCompatImageView getLogo() {
            return mLogo;
        }*/

       /* public AppCompatTextView getName() {
            return mcontent;
        }*/


        public AppCompatTextView getMQtyRate() {
            return mQtyRate;
        }

        public AppCompatTextView getMQuantity() {
            return mQuantity;
        }

        public AppCompatTextView getMunits() {
            return mUnits;
        }

        public AppCompatTextView getMContent() {
            return mContent;
        }

        public ImageView getMMinus() {
            return mMinus;
        }
    }


    class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String productUrl = AppConstants.Url.GET_PRODUCT;
                String replacedProductUrl = productUrl.replace("?", String.valueOf(category));
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + replacedProductUrl, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCategories::onGetExecte(): result is: " + result);
            if (result != null && result.trim().length() != 0) {
                productItems = JsonParserUtils.productParser(result);
                productService.syncProductCountWithCartCount(productItems);
                mProductList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                mProductList.getProgressView().setVisibility(View.GONE);
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            spinner.setVisibility(View.GONE);
        }
    }

}
