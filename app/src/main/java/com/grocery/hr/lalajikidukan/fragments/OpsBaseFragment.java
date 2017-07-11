package com.grocery.hr.lalajikidukan.fragments;


import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpsBaseFragment extends Fragment {
    Handler mHandler;
    MainActivity mActivity;
    Toolbar mToolbar;
    ActionBarDrawerToggle mDrawerToggle;
    Utils mUtils;

    public static final String TAG = OpsBaseFragment.class.getSimpleName();

  /*  @BindView(R.id.OrderAdminRv)
    RecyclerView mrecyclerView;*/


    @BindView(R.id.mRootWidget)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.admin_viewpager)
    ViewPager mviewpager;

    @BindView(R.id.sliding_tabs)
    TabLayout mtablayout;

    ViewPagerAdapter mviewPagerAdapter;

    public static OpsBaseFragment newInstance() {
        return new OpsBaseFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.showCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ops_base, container, false);
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
        mActivity.setTitle("Operation");
        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mActivity.getDrawerLayout(), mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActivity.getDrawerLayout().addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void setupViews() {
        mviewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mviewpager.setAdapter(mviewPagerAdapter);
        mtablayout.setupWithViewPager(mviewpager);

        mviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private  int NUM_ITEMS = 5;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return OpsOrderFragment.newInstance(AppConstants.OrderStatus.PLACED);
                case 1: return OpsOrderFragment.newInstance(AppConstants.OrderStatus.PREPARING);
                case 2: return OpsOrderFragment.newInstance(AppConstants.OrderStatus.DISPATCHED);
                case 3: return OpsOrderFragment.newInstance(AppConstants.OrderStatus.DELIVERED);
                case 4: return OpsOrderFragment.newInstance(AppConstants.OrderStatus.REJECTED);
                default: return null;
            }

        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return AppConstants.OrderStatus.PLACED;
                case 1: return AppConstants.OrderStatus.PREPARING;
                case 2: return AppConstants.OrderStatus.DISPATCHED;
                case 3: return AppConstants.OrderStatus.DELIVERED;
                case 4: return AppConstants.OrderStatus.REJECTED;
                default: return null;
            }
        }
    }

}
