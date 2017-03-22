package com.grocery.hr.lalajikidukan;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
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

import com.grocery.hr.lalajikidukan.fragments.CartFragment;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.utils.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.BasePrefs;
import com.grocery.hr.lalajikidukan.utils.InstancePrefs;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @BindView(R.id.flContentMain)
    FrameLayout mFrameLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.cvBottomCart)
    CardView mCart;

    @BindView(R.id.tvBottomCartItem)
    TextView mCartItem;


    private Handler mHandler;
    private AppPrefs mAppPrefs;
    private Prefs mPrefs;
    private Utils mUtils;
    InstancePrefs<List<CartModel>> cartPrefs;

   /* private AlertDialog mNetworkAlert;
    private AlertDialog mLocationAlert;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         cartPrefs = new InstancePrefs(CartFragment.TAG, MainActivity.this);
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
                /*setupView();*/
                 updateCart();
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

    public void updateCart() {
        new GetCart().execute();
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
       */
        } else if (currentSelectedPosition == 1) {/*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, AddressFragment.newInstance(),
                            AddressFragment.TAG)
                    .commit();
        */
        } else if (mPrefs.getDrawerItemPosition() == 2) {/*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, OrderFragment.newInstance(),
                            OrderFragment.TAG)
                    .commit();

        */
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

    class GetCart extends AsyncTask<Void, Void, String> {

//        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mDialog = mUtils.getProgressDialog(MainActivity.this);
//            mDialog.setMessage("Updating Cart...");
//            mDialog.setCancelable(false);
//            mDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Map<String,String> pairs = new HashMap<>();
            pairs.put("user", mAppPrefs.getMobileNo());
            pairs.put("passwd",mAppPrefs.getPassword());
            try {
                if (!cartPrefs.isSavedInstanceState()) {
                    String response=mUtils.getToServer(Utils.baseURL + "/cart", pairs);
                    return response;
                }
            } catch (Exception e) {
                Log.e(TAG, "GetCart::doInBackground(): " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCart::onPostExecute(): result is: " + result);
            if (result != null && result.trim().length() != 0) {
                List<CartModel> cartModelList = JsonParserUtils.cartParser(result);
                cartPrefs.setSavedInstanceState(true);
                cartPrefs.setServerCallResult(cartModelList);
                setupCartData(cartModelList);
            } else if (cartPrefs.isSavedInstanceState()) {
                setupCartData((List<CartModel>) cartPrefs.getServerCallResult());
            } else {
                try {
                    Snackbar.make(mDrawerLayout,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "GetCart::onPostExecute(): empty result");
            }
//            if (null != mDialog && mDialog.isShowing()) {
//                mDialog.dismiss();
//            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    showCart();

                }

            });
        }

        public void setupCartData(List<CartModel> cartModels) {
            // html Description list
           /* List<Integer> fromIndexList = new ArrayList<>();
            int index;
            do {
                // extract the html tags contains in "p_desc" key from jsonString
                if (fromIndexList.size() == 0) {
                    index = jsonString.indexOf("\"p_desc\":\"");
                } else {
                    index = jsonString.indexOf("\"p_desc\":\"",
                            fromIndexList.get(fromIndexList.size() - 1));
                }
                if (index != -1) {
                    Integer fromIndex = index + ("\"p_desc\":\"").length();
                    fromIndexList.add(fromIndex);
                    String desc = jsonString.substring(fromIndex);
                    desc = desc.substring(0, desc.indexOf("\",\"cat\""));

                    // remove the html tags from jsonString
                    jsonString = jsonString.replace(desc, "");
                }
            } while (index != -1);

            // extract remaining &nbsp; and <br /> tags
            jsonString = StringUtils.remove(jsonString, "&nbsp;");
            jsonString = StringUtils.remove(jsonString, "<br />");*/


            if (null != cartModels && cartModels.size() > 0) {
                mAppPrefs.setCartItem(cartModels.size());
            } else {
                Log.e(TAG, "GetCart::setupCartData::" +
                        " cartList is null or empty");
            }

        }

        public void showCart() {
            String cartTotal = String.valueOf(
                    NumberFormat.getInstance()
                            .format(mAppPrefs.getCartTotal())
            );
            mCartItem.setText(String.valueOf(mAppPrefs.getCartItem() + " Item(s)"));
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

        public CardView getCart() {
            return mCart;
        }

        public FrameLayout getFrameLayout() {
            return mFrameLayout;
        }

    }

}
