package com.grocery.hr.lalajikidukan;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.utils.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.BasePrefs;
import com.grocery.hr.lalajikidukan.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.reflect.Array.getInt;

/**
 * Created by vipul on 21/3/17.
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

   /* @BindView(R.id.flContentMain)
    FrameLayout mFrameLayout;*/

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

   /* @BindView(R.id.cvBottomCart)
    CardView mCart;

    @BindView(R.id.tvBottomCartItem)
    TextView mCartItem;

    @BindView(R.id.tvBottomCartTotal)
    TextView mCartTotal;*/

    private Handler mHandler;
    private AppPrefs mAppPrefs;
    private Prefs mPrefs;
    private Utils mUtils;

   /* private AlertDialog mNetworkAlert;
    private AlertDialog mLocationAlert;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new Handler();
        mAppPrefs = new AppPrefs(this);
        mPrefs = new Prefs(this);
        mUtils = Utils.getInstance();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setUpNavView();
                /*setupView();
                updateCart();*/
            }
        });
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: called");
        mAppPrefs.setRunning(false);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume: called");
        mAppPrefs.setRunning(true);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setupLocation();
            }
        });
    }*/


    public void setUpNavView() {
        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.action_home)
                .setTitle(mAppPrefs.getMobileNo());

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                int drawerItemPosition = mPrefs.getDrawerItemPosition();
                switch (menuItem.getItemId()) {
                    /*case R.id.action_home:
                        if (drawerItemPosition != 0) {
                            loadFragment(0);
                        }
                        break;
                    case R.id.action_addresses:
                        if (drawerItemPosition != 1) {
                            loadFragment(1);
                        }
                        break;*/
                    case R.id.action_orders:
                        if (drawerItemPosition != 2) {
                            loadFragment(2);
                        }
                        break;
                    /*case R.id.action_cart:
                        onCart();
                        break;
                    case R.id.action_rate:
                        mUtils.rateApp(MainActivity.this);
                        break;
                    case R.id.action_share:
                        mUtils.shareApp(MainActivity.this);
                        break;
                    case R.id.action_logout:
                        logout();
                        break;*/
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void loadFragment(int currentSelectedPosition) {
        mPrefs.setDrawerItemPosition(currentSelectedPosition);
        clearBackStack();
        if (currentSelectedPosition == 0) {/*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, HomeFragment.newInstance(),
                            HomeFragment.TAG)
                    .commit();
       */ } else if (currentSelectedPosition == 1) {/*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, AddressFragment.newInstance(),
                            AddressFragment.TAG)
                    .commit();
        */} else if (mPrefs.getDrawerItemPosition() == 2) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, OrderFragment.newInstance(),
                            OrderFragment.TAG)
                    .commit();

        }
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStackImmediate();
        }
    }

    class Prefs extends BasePrefs {

        private final String DrawerItemPosition = "DrawerItemPosition";

        public Prefs(@NonNull Context context) {
            super(TAG, context);
        }

        public int getDrawerItemPosition() {
            return getInt(DrawerItemPosition, 0);
        }

        public void setDrawerItemPosition(int drawerItemPosition) {
            putInt(DrawerItemPosition, drawerItemPosition);
        }
    }

}
