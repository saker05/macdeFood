package com.grocery.hr.lalajikidukan.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.adapters.CustomSwipeAdapter;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyCharacterMap.load;

/**
 * Created by vipul on 13/4/17.
 */

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private ActionBarDrawerToggle mDrawerToggle;
    private MainActivity mActivity;
  //  private SliderAdapter mSliderAdapter;
    private CategoryAdapter mCategoryAdapter;
    private List<ProductModel> highlightedProductItems;
    private List<CategoryModel> categoryItems;
    private Handler mHandler;
    private Utils mUtils;
    CustomSwipeAdapter mCustomPagerAdapter;
    ViewPager mViewPager;


    // Xml field
    Toolbar mToolbar;

    @BindView(R.id.sliderRootHome)
    CoordinatorLayout mRootWidget;



    @BindView(R.id.categoryslider)
   RecyclerView mcategoryslider;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    //    mSliderAdapter = new SliderAdapter();
        mCategoryAdapter = new CategoryAdapter();
        mUtils = Utils.getInstance();
        mHandler = new Handler();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!mUtils.isDeviceOnline(getContext())) {
            return inflater.inflate(R.layout.no_internet_connection, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }

    }



    public class MyTimerTask extends TimerTask
    {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int len, i;
                    len = mCustomPagerAdapter.getCount();

                    if(mViewPager.getCurrentItem()==(len-1))
                    {
                        mViewPager.setCurrentItem(0);
                    }
                    i  = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(i+1);
                }
            });
        }
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
        if(mUtils.isDeviceOnline(getContext())){
            ButterKnife.bind(this, view);
         //   mcategoryslider.getProgressView().setVisibility(View.GONE);
            mToolbar=(Toolbar)getActivity().findViewById(R.id.homeToolbar);

            mCustomPagerAdapter = new CustomSwipeAdapter(getActivity());
             mViewPager = (ViewPager) mActivity.findViewById(R.id.viewpagerhome);
            mViewPager.setAdapter(mCustomPagerAdapter);

            Timer timer = new Timer();
            timer.schedule(new MyTimerTask(), 2000,4000);



           setUpToolbar();
            setUpViews();
        }
        else
        {
            mToolbar=(Toolbar)getActivity().findViewById(R.id.noInternetConnectionToolbar);
            setUpToolbar();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public void setUpToolbar() {
        mActivity.setSupportActionBar(mToolbar);
        mActivity.setTitle(getString(R.string.app_name));
        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mActivity.getDrawerLayout(), mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActivity.getDrawerLayout().addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void setUpViews() {

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mcategoryslider.setLayoutManager(linearLayoutManager2);
        mcategoryslider.setNestedScrollingEnabled(false);
      //  mcategoryslider.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //    @Override
          //  public void onRefresh() {
    //            baseGetCategoryAndHighlihtedProducts();
        //    }
       // });
  //      baseGetCategoryAndHighlihtedProducts();

        mcategoryslider.setAdapter(mCategoryAdapter);
     //   mcategoryslider.setNestedScrollingEnabled(false);
        mCategoryAdapter.notifyDataSetChanged();
    }

    /*public void baseGetCategoryAndHighlihtedProducts() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetCategories().execute();
               // new GetHighlightedProducts().execute();
            }
        });
    }
*/

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
           /* CategoryModel categoryModel = categoryItems.get(position);
            holder.getMCategoryName().setText(categoryModel.getName());
            holder.getMDescription().setText(categoryModel.getDescription());*/
        }

        @Override
        public int getItemCount() {
            return 5;
          /*  if (categoryItems != null) {
                return categoryItems.size();
            }
            return 0;*/
        }
    }


    class CategorySliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CategorySliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   mHeader.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @BindView(R.id.categoryImage)
        ImageView mCategoryImage;

        @BindView(R.id.categroyName)
        TextView mCategoryName;

        @BindView(R.id.description)
        TextView mDescription;

        @Override
        public void onClick(View v) {
            CategoryModel category = categoryItems.get(getAdapterPosition());
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, ProductFragment
                                    .newInstance(category.getId()),
                            ProductFragment.TAG).addToBackStack(null)
                    .commit();
        }

        public TextView getMCategoryName() {
            return mCategoryName;
        }

        public TextView getMDescription() {
            return mDescription;
        }

    }

/*
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
                     Picasso.with(mActivity)
                    .load(R.drawable.placeholder)
                    .into(holder.getmSliderImage());
        }

        @Override
        public int getItemCount() {
            if (highlightedProductItems != null) {
                return highlightedProductItems.size();
            }
            return 0;
        }
    }


    class SliderCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public SliderCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.category_slider_image)
        ImageView mSliderImage;

        @BindView(R.id.vpRootSlider1)
        ViewPager mPager;

        @Override
        public void onClick(View v) {

        }

        public ImageView getmSliderImage() {
            return mSliderImage;
        }
    }


    class GetHighlightedProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.GET_HIGHLIGHTED_PRODUCT, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetHighLightedProduct::onGetExecte(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
       //         highlightedProductItems = JsonParserUtils.productParser(result);
         //       mCardSlider.setAdapter(mSliderAdapter);
                mSliderAdapter.notifyDataSetChanged();
            } else {
                try {
           //         mCardSlider.getProgressView().setVisibility(View.GONE);
             //       mCardSlider.setAdapter(mSliderAdapter);
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //mUtils.hideRefreshing(mCardSlider);
        }
    }*/


    /*class GetCategories extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.GET_CATEGORY, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCategories::onGetExecte(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                categoryItems = JsonParserUtils.categoryParser(result);

                mcategoryslider.setAdapter(mCategoryAdapter);
                mCategoryAdapter.notifyDataSetChanged();
            } else {
                mcategoryslider.getProgressView().setVisibility(View.GONE);
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mUtils.hideRefreshing(mcategoryslider);
        }
    }
*/

}



