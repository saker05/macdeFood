package com.grocery.hr.lalajikidukan.fragments;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul on 13/4/17.
 */

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;

    MainActivity mActivity;


    @BindView(R.id.sliderRootHome)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.sliderToolbar)
    Toolbar mToolbar;

    @BindView(R.id.cardslider)
    SuperRecyclerView mCardSlider;

    @BindView(R.id.categoryslider)
      SuperRecyclerView mcategoryslider;






    SliderAdapter mSliderAdapter;

    CategoryAdapter mCategoryAdapter;



    List<CartDO> cartDOs=null;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mSliderAdapter=new SliderAdapter();
        mCategoryAdapter=new CategoryAdapter();
        cartDOs=new ArrayList<>();
        CartDO a=new CartDO();
        cartDOs.add(a);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            menu.clear();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpViews();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public void setUpViews() {

        mActivity.setSupportActionBar(mToolbar);
        /*ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        mActivity.setTitle(getString(R.string.app_name));

        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mActivity.getDrawerLayout(), mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActivity.getDrawerLayout().addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCardSlider.setLayoutManager(linearLayoutManager1);

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mcategoryslider.setLayoutManager(linearLayoutManager2);




        mcategoryslider.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();

        mCardSlider.setAdapter(mSliderAdapter);
        mSliderAdapter.notifyDataSetChanged();

    }


   /* public void setUpViews1()
    {

        mActivity.setSupportActionBar(mToolbar);
        *//*ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*//*
        mActivity.setTitle(getString(R.string.app_name));

        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mActivity.getDrawerLayout(), mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActivity.getDrawerLayout().addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mcategoryslider.setLayoutManager(linearLayoutManager);
        mCategoryAdapter.notifyDataSetChanged();



    }*/

    class CategoryAdapter extends RecyclerView.Adapter<CategorySliderViewHolder> {

        @Override
        public CategorySliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategorySliderViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.category, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(CategorySliderViewHolder holder, int position) {

        }



        @Override
        public int getItemCount() {
            return 3;
        }
    }


    class CategorySliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CategorySliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   mHeader.setVisibility(View.GONE);
            // itemView.setOnClickListener(this);
        }

        @BindView(R.id.categoryImage)
        ImageView mCategoryImage;

        @BindView(R.id.desription)
        TextView mDescription;

        @BindView(R.id.subdesription)
        TextView msubDescription;

        @Override
        public void onClick(View v) {

        }
        public TextView getmDescription()
        {
            return mDescription;
        }

        public TextView getMsubDescription()
        {
            return msubDescription;
        }



    }


    class SliderAdapter extends RecyclerView.Adapter<SliderCardViewHolder> {

        @Override
        public SliderCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SliderCardViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.highlighted_product_slider, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(SliderCardViewHolder holder, int position) {
            /*Picasso.with(mActivity)
                    .load(R.drawable.placeholder)
                    .into(holder.getmSliderImage());*/
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }


    class SliderCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public SliderCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   mHeader.setVisibility(View.GONE);
            // itemView.setOnClickListener(this);
        }

        @BindView(R.id.category_slider_image)
        ImageView mSliderImage;

       /* @BindView(R.id.vpRootSlider1)
        ViewPager mPager;*/


        @Override
        public void onClick(View v) {

        }

        public ImageView getmSliderImage() {
            return mSliderImage;
        }
    }



    class GetHighlightedProduct extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCart::onPostExecute(): result is: " + result);

        }
    }

}



