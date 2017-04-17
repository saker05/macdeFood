package com.grocery.hr.lalajikidukan.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.UserSubOrderModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

/**
 * Created by vipul on 17/4/17.
 */

public class SubOrderFragment extends Fragment {

    private Utils mUtils;

    private List<UserSubOrderModel> subOrders;
    public static final String TAG = SubOrderFragment.class.getSimpleName();

    String  uoc;

    class GetSubOrders extends AsyncTask<Void, Void, String>

    {

        @Override
        protected String doInBackground(Void... params) {
        try {
            String subOrderUrl=AppConstants.Url.GET_SUB_ORDERS;
            String replacedSubOrderUrl=subOrderUrl.replace("?",uoc);
            return mUtils.getFromServer(AppConstants.Url.BASE_URL + subOrderUrl, mUtils.getUerPasswordMap(getContext()));
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
            subOrders= JsonParserUtils.subOrderParser(result);
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
