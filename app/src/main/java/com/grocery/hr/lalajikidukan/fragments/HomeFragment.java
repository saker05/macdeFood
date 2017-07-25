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
import com.grocery.hr.lalajikidukan.adapters.HighlighterAutoSwipeAdapter;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.manager.PicassoManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.preferences.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.CloudinaryUtility;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul on 13/4/17.
 */

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private ActionBarDrawerToggle mDrawerToggle;
    private MainActivity mActivity;
    private CategoryAdapter mCategoryAdapter;
    private List<ProductModel> highlightedProductItems;
    private List<CategoryModel> categoryItems;
    private Handler mHandler;
    private Utils mUtils;
    HighlighterAutoSwipeAdapter mCustomPagerAdapter;
    private PicassoManager picassoManager;
    AppPrefs appPrefs;
    Timer timer;


    // Xml field
    Toolbar mToolbar;

    @BindView(R.id.cl_root)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.rv_category)
    RecyclerView mRecyclerCategory;

    @BindView(R.id.viewpager_highlighters)
    ViewPager mViewPager;

    @BindView(R.id.ll_spinner)
    LinearLayout mSpinner;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mCategoryAdapter = new CategoryAdapter();
        mUtils = Utils.getInstance();
        mHandler = new Handler();
        timer = new Timer();
        picassoManager = PicassoManager.getInstance();
        appPrefs=AppPrefs.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!mUtils.isDeviceOnline(getContext())) {
            return inflater.inflate(R.layout.fragment_no_internet_connection, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_home, container, false);
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
        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            setUpToolbar();
            setUpViews();
        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerCategory.setLayoutManager(linearLayoutManager);
        baseGetCategoryAndHighlihtedProducts();
    }

    public void baseGetCategoryAndHighlihtedProducts() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetCategories().execute();
                new GetHighlightedProducts().execute();
            }
        });
    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if(getActivity()==null){
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int len, i;
                    len = mCustomPagerAdapter.getCount();
                    if (mViewPager.getCurrentItem() == (len - 1)) {
                        mViewPager.setCurrentItem(0);
                    }
                    i = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(i + 1);
                }
            });
        }
    }


    class CategoryAdapter extends RecyclerView.Adapter<CategorySliderViewHolder> {

        @Override
        public CategorySliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategorySliderViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_category_home, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(CategorySliderViewHolder holder, int position) {
            CategoryModel categoryModel = categoryItems.get(position);
            String imageUrl = CloudinaryUtility.getResizeImageUrl(100, 100, categoryModel.getImageUrl());
            picassoManager.downloadImage(getContext(), imageUrl, holder.getmCategoryImage());
            holder.getMCategoryName().setText(categoryModel.getName());
            holder.getMDescription().setText(categoryModel.getDescription());
        }

        @Override
        public int getItemCount() {
            if (categoryItems != null) {
                return categoryItems.size();
            }
            return 0;
        }
    }


    class CategorySliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CategorySliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @BindView(R.id.image)
        ImageView mCategoryImage;

        @BindView(R.id.text_name)
        TextView mCategoryName;

        @BindView(R.id.text_description)
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

        public ImageView getmCategoryImage() {
            return mCategoryImage;
        }

    }


    class GetHighlightedProducts extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL +
                        AppConstants.Url.GET_HIGHLIGHTED_PRODUCT, null);
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
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    highlightedProductItems = JsonParserUtils.productParser(result);
                    mCustomPagerAdapter = new HighlighterAutoSwipeAdapter(getActivity(), highlightedProductItems);
                    if(!appPrefs.isHighligherAutoSwiperThreadIsRunning()){
                        timer.schedule(new MyTimerTask(), 2000, 4000);
                        appPrefs.setHighligherAutoSwiperThreadIsRunning(true);
                    }
                    mViewPager.setAdapter(mCustomPagerAdapter);
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
        }
    }


    class GetCategories extends AsyncTask<Void, Void, String> {

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
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    categoryItems = JsonParserUtils.categoryParser(result);
                    mRecyclerCategory.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
            mSpinner.setVisibility(View.GONE);
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



