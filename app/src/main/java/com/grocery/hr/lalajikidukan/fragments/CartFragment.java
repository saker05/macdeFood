package com.grocery.hr.lalajikidukan.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.adapters.*;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private ArrayList countries;
    public static final String TAG= CartFragment.class.getSimpleName();
    private Utils utils=Utils.getInstance();

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance()
    {
        return  new CartFragment();
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View recycleview = inflater.inflate(R.layout.fragment_cart, container, false);




        RecyclerView recyclerView = (RecyclerView)recycleview.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        countries = new ArrayList<>();
        countries.add("Australia");
        countries.add("India");
        countries.add("United States of America");
        countries.add("Germany");
        countries.add("Russia");
        countries.add("Russia1");
        countries.add("Russia2");
        countries.add("Russia3");
        countries.add("Russia4");
        countries.add("Russia5");
        countries.add("Russia6");
        countries.add("Russia7");
        countries.add("Russia8");

        new GetCart().execute();
        DataAdapter adapter = new DataAdapter(countries);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    // Toast.makeText(getContext(),countries.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return recycleview;
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
            Map<String, String> pairs = new HashMap<>();
            pairs.put("user","9729012780");
            pairs.put("passwd","vipul");
            try {
                List<CartModel> cartModels=new ArrayList<>();
                CartModel cartModel=new CartModel();
                cartModel.setUnitQuantityInGm(12);
                cartModel.setUnitAmount(43);
                cartModel.setUpc("abc");
                cartModel.setNoOfUnits(12);
                cartModels.add(cartModel);
                String data=new Gson().toJson(cartModels);
                return utils.postToServer(AppConstants.Url.BASE_URL + "/cart", pairs,data);
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
                List<CartModel> cartDOs= JsonParserUtils.cartParser(result);
               /* InstancePrefs cartPrefs = new InstancePrefs(CartFragment.TAG, MainActivity.this);
                cartPrefs.setServerCallResult(result);
                setupCartData(result);*/
            }  /*{
                try {
                    Snackbar.make(mDrawerLayout,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "GetCart::onPostExecute(): empty result");
            }*/
//            if (null != mDialog && mDialog.isShowing()) {
//                mDialog.dismiss();
//            }
           /* mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mAppPrefs.getCartItem() != -1
                            && mAppPrefs.getCartTotal() != -1) {
                        showCart();
                    } else {
                        hideCart();
                    }
                }
            });*/
        }


    }


}


