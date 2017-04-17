package com.grocery.hr.lalajikidukan.fragments;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.util.Util;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

/**
 * Created by vipul on 17/4/17.
 */

public class AddressFragment extends Fragment{

    public static final String TAG = AddressFragment.class.getSimpleName();

    private Utils mUtils;

    private List<AddressModel> addresses;







    class GetAddress extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.GET_ADDRESS, mUtils.getUerPasswordMap(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetAddress::onPostExecute(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                addresses=JsonParserUtils.addressParser(result);
                /*mCartList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();*/
            } else {
              /*  try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }
    }



}
