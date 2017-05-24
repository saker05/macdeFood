package com.grocery.hr.lalajikidukan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.fragments.AddressFragment;
import com.grocery.hr.lalajikidukan.fragments.CartFragment;
import com.grocery.hr.lalajikidukan.fragments.HomeFragment;
import com.grocery.hr.lalajikidukan.fragments.ProductFragment;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.preferences.AppPrefs;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.service.MyFirebaseInstanceIdService;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 21/3/17.
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.flContentMain)
    FrameLayout mFrameLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.cvBottomCart)
    CardView mCart;

    @BindView(R.id.tvBottomCartItem)
    TextView mCartItem;

    private CartManager cartManager;

    private Handler mHandler;

    private Utils mUtils;

    private AppPrefs appPrefs;

    private AlertDialog mNetworkAlert;
    private AlertDialog mLocationAlert;

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cartManager = CartManager.getInstance(this);
        mHandler = new Handler();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        appPrefs = AppPrefs.getInstance();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setUpNavView();
                setupView();
                showCart();
                doesNotificationClicked();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        appPrefs.setRunning(false);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        appPrefs.setRunning(true);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setupLocation();
            }
        });
        setUpNavView();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0
                && appPrefs.getActivityDrawerItemPosition() != 0) {
            loadFragment(0);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.cvBottomCart)
    public void onCart() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContentMain, CartFragment.newInstance(), CartFragment.TAG)
                .addToBackStack(CartFragment.TAG)
                .commit();
    }

    public void setupView() {
        loadFragment(appPrefs.getActivityDrawerItemPosition());
    }


    public void setUpNavView() {
        Menu menu = mNavigationView.getMenu();
        if (mUtils.isUserLoggedIn(getApplicationContext())) {
            menu.findItem(R.id.action_home)
                    .setTitle(AppSharedPreference.getString(this, AppConstants.User.MOBILE_NO));
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
            if (AppSharedPreference.getString(getApplicationContext(), AppConstants.User.TYPE) != null &&
                    !AppSharedPreference.getString(getApplicationContext(), AppConstants.User.TYPE).equals(AppConstants.USER_TYPE.NORMAL)) {
                menu.findItem(R.id.action_role).setTitle(AppSharedPreference.getString(getApplicationContext(), AppConstants.User.TYPE)).setVisible(true);
            }
        } else {
            menu.findItem(R.id.action_home)
                    .setTitle("");
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(true);
            menu.findItem(R.id.action_role).setVisible(false);
        }


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                int drawerItemPosition = appPrefs.getActivityDrawerItemPosition();
                switch (menuItem.getItemId()) {
                    case R.id.action_login:
                        loadLoginActivity();
                        break;
                    case R.id.action_home:
                        if (drawerItemPosition != 0) {
                            loadFragment(0);
                        }
                        break;
                    case R.id.action_addresses:
                        if (drawerItemPosition != 1) {
                            loadFragment(1);
                        }
                        break;
                    case R.id.action_orders:
                        loadFragment(2);
                        break;
                    case R.id.action_cart:
                        onCart();
                        break;
                    /*case R.id.action_rate:
                        mUtils.rateApp(MainActivity.this);
                        break;
                    case R.id.action_share:
                        mUtils.shareApp(MainActivity.this);
                        break; */
                    case R.id.action_logout:
                        logout();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void logout() {
        new PostTokenToServer().execute();
    }

    public void loadFragment(int currentSelectedPosition) {
        appPrefs.setActivityDrawerItemPosition(currentSelectedPosition);
        clearBackStack();
        if (currentSelectedPosition == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, HomeFragment
                                    .newInstance(),
                            HomeFragment.TAG)
                    .commit();
        } else if (currentSelectedPosition == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, AddressFragment.newInstance(AppConstants.Address_Fragment.MY_ADDRESSES),
                            AddressFragment.TAG)
                    .commit();
        } else if (currentSelectedPosition == 2) {
            /*getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, CartFragment.newInstance(),
                            CartFragment.TAG)
                    .commit();*/
        }
    }

    public void loadLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void showCart() {
        int noOItemsInCart = cartManager.noOfItemInCart();
        mCartItem.setText(String.valueOf(noOItemsInCart + " Item(s)"));
        getCart().post(new Runnable() {
            @Override
            public void run() {
                getCart().setVisibility(View.VISIBLE);
            }
        });
        getFrameLayout().post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams =
                        (FrameLayout.LayoutParams) getFrameLayout().getLayoutParams();
                layoutParams.bottomMargin = mUtils.getActionBarSize(MainActivity.this);
            }
        });
    }


    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }


    public void hideCart() {
        getCart().post(new Runnable() {
            @Override
            public void run() {
                getCart().setVisibility(View.GONE);
            }
        });
        getFrameLayout().post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams =
                        (FrameLayout.LayoutParams) getFrameLayout().getLayoutParams();
                layoutParams.bottomMargin = 0;
            }
        });
    }

    public CardView getCart() {
        return mCart;
    }

    public FrameLayout getFrameLayout() {
        return mFrameLayout;
    }

    public void setupLocation() {
        if (null != mNetworkAlert && mNetworkAlert.isShowing()) {
            mNetworkAlert.dismiss();
        }

        if (null != mLocationAlert && mLocationAlert.isShowing()) {
            mLocationAlert.dismiss();
        }

        if (!mUtils.isDeviceOnline(this)) {
            mNetworkAlert = mUtils.showMessage(this,
                    getString(R.string.network_alert_title),
                    getString(R.string.network_alert_message),
                    "OK",
                    null);
        }
        mLocationAlert = mUtils.checkLocationSettings(this);
    }

    public void doesNotificationClicked() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String action = extras.getString("action");
            if (AppConstants.Notification.NOTIFICATION_ACTION_CART.equals("cart")) {
                loadFragment(2);
            }
        }
    }

    class PostTokenToServer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.putToServer(AppConstants.Url.BASE_URL + AppConstants.Url.ANONYMOUS_TOKEN_PATH,
                        null, AppSharedPreference.getString(getApplicationContext(),
                                AppConstants.Notification.KEY_ACCESS_TOKEN));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCart::onPostExecute(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    mUtils.showMessage(
                            MainActivity.this,getString(R.string.complaint_to_admin),
                            "Try again later.\nTry Again",
                            "OK", null
                    );
                } else {
                    AppSharedPreference.remove(getApplicationContext(), AppConstants.User.MOBILE_NO);
                    AppSharedPreference.remove(getApplicationContext(), AppConstants.User.PASSWORD);
                    AppSharedPreference.remove(getApplicationContext(), AppConstants.User.TYPE);
                    setUpNavView();
                }
            } else {
                mUtils.showMessage(
                        MainActivity.this,
                        getString(R.string.cant_connect_to_server),
                        "Try again later.\nTry Again",
                        "OK", null
                );
            }
        }
    }


}
