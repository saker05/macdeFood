package com.grocery.hr.lalajikidukan.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul on 13/4/17.
 */

public class ProductFragment extends Fragment {

    public static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.clRootproduct)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.pToolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvproduct)
    SuperRecyclerView mProductList;



    private ProductAdapter mAdapter;

    private MainActivity mActivity;



    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        mAdapter = new ProductAdapter();


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
        return inflater.inflate(R.layout.product, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //mActivity.hideCart();
        setUpViews();
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

        mActivity.setTitle("Product");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProductList.setLayoutManager(linearLayoutManager);
        mProductList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();



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
           // CartModel item = cartItems.get(position);
           /* Picasso.with(mActivity)
                    .load(R.drawable.placeholder)
                    .into(holder.getLogo());*/

        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }



    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


/*
        @BindView(R.id.imageView)
       AppCompatImageView mLogo;*/

        @BindView(R.id.productcontent)
        AppCompatTextView mcontent;

        @BindView(R.id.productquantity)
        AppCompatTextView mquantity;

        @BindView(R.id.productrupee)
        AppCompatTextView mQtyRate;

        @BindView(R.id.productNoofunits)
        AppCompatTextView munits;

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

       /* public AppCompatTextView getName() {
            return mcontent;
        }*/


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



}
