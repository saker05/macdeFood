package com.grocery.hr.lalajikidukan.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 17/4/17.
 */

public class EditOrAddAddressFragment extends Fragment implements TextWatcher {

    public static final String TAG = EditOrAddAddressFragment.class.getSimpleName();

    public EditOrAddAddressFragment(AddressModel address) {
        this.address = address;
    }

    public EditOrAddAddressFragment() {

    }

    public static EditOrAddAddressFragment newInstance() {
        return new EditOrAddAddressFragment();
    }

    public static EditOrAddAddressFragment newInstance(AddressModel address) {
        return new EditOrAddAddressFragment(address);
    }

    private Utils mUtils;
    private AddressModel address = null;
    private MainActivity mActivity;
    private Handler mHandler;
    private Gson gson;
    private String addressJson;
    private boolean isNameOk=false;
    private boolean isPincodeOk=false;
    private boolean isLocalityOk=false;
    private boolean isFlatNoOk  = false;
    private boolean isPhoneNoOk = false;

    @BindView(R.id.add_addresstb)
    Toolbar mToolbar;

    @BindView(R.id.customer_name)
    EditText mName;

    @BindView(R.id.customer_pincode)
    EditText mPincode;

    @BindView(R.id.customer_flat_no)
    EditText mFlatNo;

    @BindView(R.id.customer_locality)
    EditText mLocality;

    @BindView(R.id.customer_landmark)
    EditText mLandmark;

    @BindView(R.id.customer_phone_number)
    EditText mPhoneNo;


    @BindView(R.id.radioGroup)
    RadioGroup mAddressType;

    @BindView(R.id.btn_update)
    AppCompatButton mUpdateButton;

    @BindView(R.id.clRootAddressUpate)
    RelativeLayout mRootWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        gson = new Gson();
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
        if (!mUtils.isDeviceOnline(getContext())) {
            return inflater.inflate(R.layout.fragment_no_internet_connection, container, false);
        } else {
            return inflater.inflate(R.layout.add_address, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.hideCart();
        ButterKnife.bind(this, view);
        setUpToolbar();
        setUpViews();
        validateWidgets();
    }

    public void validateWidgets() {
        mName.addTextChangedListener(this);
        mPincode.addTextChangedListener(this);
        mLocality.addTextChangedListener(this);
        mFlatNo.addTextChangedListener(this);
        mPhoneNo.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        View view = mActivity.getCurrentFocus();
        if (null != view) {
            switch (view.getId()) {
                case R.id.customer_name:
                    validateName(s);
                    break;
                case R.id.customer_pincode:
                    validatePincode(s);
                    break;
                case R.id.customer_flat_no:
                    validateFlatNo(s);
                    break;
                case R.id.customer_locality:
                    validateLocality(s);
                    break;
                case R.id.customer_phone_number:
                    validatePhoneNo(s);
                    break;
            }
        }
    }

    public void validateName(Editable s) {
        isNameOk = !TextUtils.isEmpty(s);
        setError(isNameOk, mName, "Please, Enter Valid Name");
        setBnUpdateAddress();
    }

    public void validatePincode(Editable s) {
        isPincodeOk = s.length() == 6;
        setError(isPincodeOk, mPincode, "Please, Enter Valid Pincode");
        setBnUpdateAddress();
    }

    public void validateFlatNo(Editable s) {
        isFlatNoOk = !TextUtils.isEmpty(s);
        setError(isFlatNoOk, mFlatNo, "Please, Enter Valid flat number");
        setBnUpdateAddress();
    }

    public void validateLocality(Editable s) {
        isLocalityOk = !TextUtils.isEmpty(s);
        setError(isLocalityOk, mLocality, "Please, Enter Valid locality");
        setBnUpdateAddress();
    }

    public void validatePhoneNo(Editable s) {
        isPhoneNoOk = s.length()==10;
        setError(isPhoneNoOk, mPhoneNo, "Please, Enter Valid Phone no");
        setBnUpdateAddress();
    }


    public void setError(boolean isOK, final EditText editText, final String message) {
        if (!isOK) {
            editText.post(new Runnable() {
                @Override
                public void run() {
                    editText.setError(message);
                }
            });
        } else {
            editText.post(new Runnable() {
                @Override
                public void run() {
                    editText.setError(null);
                }
            });
        }
    }

    public void setBnUpdateAddress() {
        mUpdateButton.setEnabled((isFlatNoOk && isNameOk && isLocalityOk && isPincodeOk && isPhoneNoOk ));
    }


    public void setUpToolbar() {
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
        if (address == null) {
            mActivity.setTitle(getString(R.string.action_add_address));
        } else {
            mActivity.setTitle(getString(R.string.action_edit_address));
        }
    }

    public void setUpViews() {
        if (address != null) {
            mName.setText(address.getName());
            mPincode.setText(String.valueOf(address.getPincode()));
            mFlatNo.setText(address.getFlat());
            mLocality.setText(address.getLocality());
            mLandmark.setText(address.getLandmark());
            mPhoneNo.setText(address.getPhoneNumber());
            if (address.getAddressType() == "Home") {
                mAddressType.check(0);
            } else if (address.getAddressType() == "Work") {
                mAddressType.check(0);
            } else if (address.getAddressType() == "Office") {
                mAddressType.check(0);
            }
            mUpdateButton.setText("Edit Address");
            isLocalityOk=true;
            isNameOk=true;
            isFlatNoOk=true;
            isPincodeOk=true;
            isPhoneNoOk=true;
            setBnUpdateAddress();
        }else{
            mUpdateButton.setText("Add Address");
            setBnUpdateAddress();
        }
    }

    @OnClick(R.id.btn_update)
    public void onClickUpdate() {
        mUtils.hideSoftKeyboard(mActivity);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mUtils.isDeviceOnline(mActivity)) {
                   new DoUpdate().execute();
                } else {
                    Snackbar.make(mRootWidget, getString(R.string.device_offline), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    class DoUpdate extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        String name;
        int pincode;
        String flatNo;
        String locality;
        String landMark;
        String addressType;
        String phoneNo;


        public DoUpdate() {
            super();
            dialog = mUtils.getProgressDialog(mActivity);
            if (address == null) {
                address = new AddressModel();
            }
            address.setName(mName.getText().toString());
            address.setPincode(Integer.valueOf(mPincode.getText().toString()));
            address.setFlat(mFlatNo.getText().toString());
            address.setLocality(locality = mLocality.getText().toString());
            address.setLandmark(mLandmark.getText().toString());
            address.setPhoneNumber(mPhoneNo.getText().toString());
            address.setAddressType(((RadioButton) mActivity.findViewById(mAddressType.getCheckedRadioButtonId())).getText().toString());
            address.setUser(AppSharedPreference.getString(getContext(),AppConstants.User.MOBILE_NO));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addressJson = gson.toJson(address);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                if (address.getId() != 0) {
                    return mUtils.putToServer(AppConstants.Url.BASE_URL + AppConstants.Url.EDIT_ADDRESS, mUtils.getUerPasswordMap(getContext()), addressJson);
                } else {
                    return mUtils.postToServer(AppConstants.Url.BASE_URL + AppConstants.Url.ADD_NEW_ADDRESS, mUtils.getUerPasswordMap(getContext()), addressJson);
                }
            } catch (Exception e) {
                Log.e(TAG, "DoUpdate::doInBackground(): " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result != null && result.length() != 0) {
                Log.e(TAG + " result", result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj != null && jsonObj.getInt("responseCode") == 200 && jsonObj.getString("data")!=null && jsonObj.getString("data").equals("SUCCESS")) {
                        getFragmentManager().popBackStackImmediate();
                    } else {
                        mUtils.showMessage(
                                mActivity,
                                "Error",
                                "There is some issue.\nContact app help center",
                                "OK", null
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {
                Log.e(TAG, "DoLogin::onPostExecute(): unexpected server result");
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
