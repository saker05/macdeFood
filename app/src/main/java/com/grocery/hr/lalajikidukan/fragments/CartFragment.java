package com.grocery.hr.lalajikidukan.fragments;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
//import com.gripxtech.kasimrangwala.grophers.models.CartItem;
import com.grocery.hr.lalajikidukan.utils.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.InstancePrefs;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment {

    public static final String TAG = CartFragment.class.getSimpleName();

  /*  @BindView(R.id.clRootCart)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.tbToolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvCart)
    SuperRecyclerView mCartList;

    private CartAdapter mAdapter;

    //    private ActionBarDrawerToggle mDrawerToggle;
    private MainActivity mActivity;
    private Handler mHandler;
    private AppPrefs mAppPrefs;
    private InstancePrefs mPrefs;
    private Utils mUtils;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        mAppPrefs = new AppPrefs(mActivity);
        mPrefs = new InstancePrefs(TAG, mActivity);
        mAdapter = new CartAdapter(new ArrayList<CartItem>());
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mAppPrefs.getCartItem() != -1
                && mAppPrefs.getCartTotal() != -1) {
            mActivity.hideCart();
        }
        setUpViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.updateCart();
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

        mActivity.setTitle(getString(R.string.action_cart));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCartList.setLayoutManager(linearLayoutManager);
        mCartList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseGetCart();
            }
        });

        String jsonString = mPrefs.getServerCallResult();
        if (jsonString != null && !jsonString.isEmpty()) {
            // First load data from from cache
            mCartList.setAdapter(mAdapter);
            setupCartList();
        }
        baseGetCart();
    }

    public void setupCartList() {
        String jsonString = mPrefs.getServerCallResult();
        // Log.e(TAG, "setupProductList(): jsonString is: " + jsonString);
        mAdapter.getCartItems().clear();

        // html Description list
        List<String> descList = new ArrayList<>();

        List<Integer> fromIndexList = new ArrayList<>();
        int index;
        do {
            // extract the html tags from jsonString
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
                descList.add(desc);

                // remove the html tags from jsonString
                jsonString = jsonString.replace(desc, "");
            }
        } while (index != -1);

        // extract remaining &nbsp; and <br /> tags
        jsonString = StringUtils.remove(jsonString, "&nbsp;");
        jsonString = StringUtils.remove(jsonString, "<br />");

        // Log.e(TAG, "setupCartList(): jsonString is: " + jsonString);
        try {
            JSONArray cartList = new JSONObject(jsonString)
                    .optJSONArray("cart");
            if (null != cartList && cartList.length() > 0) {
                for (int i = 0; i < cartList.length(); i++) {
                    JSONObject cart = cartList.getJSONObject(i);
                    String id = cart.getString("pid");
                    String logo = ServerData.Cart.ImageURL +
                            cart.getString("image");
                    String name = cart.getString("pname");
                    String shortDesc = cart.getString("shortdisc");
                    // String desc = cart.getString("p_desc");
                    String catID = cart.getString("cat");
                    String subCatID = cart.getString("Subcat");
                    String code = cart.getString("pcode");
                    String sku = cart.getString("sku");
                    String discount = cart.getString("discount");
                    String size = cart.getString("size");
                    String mrp = cart.getString("mrp");
                    String rate = cart.getString("rate");
                    String notification = cart.getString("noti");
                    String weight = cart.getString("pweight");
                    String vendorID = cart.getString("vendor_id");
                    String todayOffer = cart.getString("todayoffer");
                    String featureProductID = cart.getString("feactureproduct");
                    String status = cart.getString("status");
                    String hot = cart.getString("hot");
                    String bestSeller = cart.getString("bestseller");
                    String shippingCharge = cart.getString("shipingcharge");
                    String pinCode = cart.getString("pincode");
                    String deliveryTime = getString(R.string.delivery_time);

                    String cartID = cart.getString("cartid");
                    String userID = cart.getString("uid");
                    String quantity = cart.getString("qty");
                    String delivery = cart.getString("delivery");
                    String sellerID = cart.getString("id");
                    String sellerShopName = cart.getString("shopname");
                    String sellerRating = cart.getString("Rating");
                    String sellerImage = ServerData.Cart.ImageURL +
                            cart.getString("simage");
                    mAdapter.getCartItems().add(new CartItem(
                            id, logo, name, shortDesc, descList.get(i), catID, subCatID, code,
                            sku, discount, size, mrp, rate, notification, weight, vendorID,
                            todayOffer, featureProductID, status, hot, bestSeller,
                            shippingCharge, pinCode, deliveryTime, cartID, userID, quantity,
                            delivery, sellerID, sellerShopName, sellerRating, sellerImage
                    ));
                }
            } else {
                Log.e(TAG, "GetCartList::onPostExecute():" +
                        " cartList is null or empty");
            }
        } catch (JSONException e) {
            Log.e(TAG, "GetCartList::onPostExecute() - JSONException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "GetCartList::onPostExecute() - Exception: " + e.getMessage());
        }
        mAdapter.notifyDataSetChanged();
    }

    public void baseGetCart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetCartList().execute();
            }
        });
    }

    class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        private List<CartItem> cartItems;

        public CartAdapter(List<CartItem> cartItems) {
            super();
            this.cartItems = cartItems;
        }

        @Override
        public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CartViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_cart, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(CartViewHolder holder, int position) {
            CartItem item = cartItems.get(position);
            Picasso.with(mActivity)
                    .load(item.getLogo())
                    .into(holder.getLogo());
            holder.getName().setText(item.getName());
            holder.getDeliveryTime().setText(item.getDeliveryTime());
            holder.getQtyRate().setText(String.valueOf(
                    item.getQuantity() + " X " + item.getRate()
            ));
            holder.getTotal().setText(String.valueOf(
                    NumberUtils.toInt(item.getQuantity(), 0)
                            * NumberUtils.toInt(item.getRate(), 0)
            ));
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        public List<CartItem> getCartItems() {
            return cartItems;
        }
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivCartHeader)
        AppCompatImageView mHeader;

        @BindView(R.id.ivCartLogo)
        AppCompatImageView mLogo;

        @BindView(R.id.tvCartName)
        AppCompatTextView mName;

        // @BindView(R.id.tvDeliveryBy)
        // AppCompatTextView mDeliveryBy;

        @BindView(R.id.tvCartDeliveryTime)
        AppCompatTextView mDeliveryTime;

        @BindView(R.id.tvCartQtyRate)
        AppCompatTextView mQtyRate;

        @BindView(R.id.tvCartTotal)
        AppCompatTextView mTotal;

        public CartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mHeader.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CartItem item = mAdapter.getCartItems().get(getAdapterPosition());
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, ProductDetailFragment.newInstance(
                            item.toString()
                    ), ProductDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }

        public AppCompatImageView getLogo() {
            return mLogo;
        }

        public AppCompatTextView getName() {
            return mName;
        }

        public AppCompatTextView getDeliveryTime() {
            return mDeliveryTime;
        }

        public AppCompatTextView getQtyRate() {
            return mQtyRate;
        }

        public AppCompatTextView getTotal() {
            return mTotal;
        }
    }

    class GetCartList extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            List<Pair<String, String>> pairs = new ArrayList<>();
            pairs.add(new Pair<>("uid", mAppPrefs.getUserID()));
            try {
                return mUtils.getToServer(ServerData.Cart.URL, pairs);
                // return mUtils.getToServer("https://gist.githubusercontent.com/kasim1011/c9dd67a7443a2b11befa0d7fc4e1503b/raw/dc60836cb3e5a02af7203de313705d22d579b8fb/gistfile1.txt", null);
            } catch (Exception e) {
                Log.e(TAG, "GetCartList::doInBackground(): " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCartList::onPostExecute(): result is: " + result);
            String jsonString = mPrefs.getServerCallResult();
            if (result != null && result.trim().length() != 0) {
                if (jsonString == null || jsonString.trim().isEmpty()) {
                    // Very First Time
                    mPrefs.setServerCallResult(result);
                    mCartList.setAdapter(mAdapter);
                    setupCartList();
                } else if (!result.equals(jsonString)) {
                    mPrefs.setServerCallResult(result);
                    setupCartList();
                }
            } else {
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonString == null || jsonString.trim().isEmpty()) {
                    // Very First Time
                    mCartList.setAdapter(mAdapter);
                }
            }
            mUtils.hideRefreshing(mCartList);
        }
    }

    class ServerData {
        public class Cart {
            public static final String URL = Utils.baseURL + "getcart.aspx";
            public static final String ImageURL = "http://foooddies.com/admin/";
        }
    }
*/
}