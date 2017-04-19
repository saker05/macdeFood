package com.grocery.hr.lalajikidukan.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import com.grocery.hr.lalajikidukan.LoginActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 18/4/17.
 */

public class SignupFragment extends Fragment implements TextWatcher {

    public static final String TAG = SignupFragment.class.getSimpleName();

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    private LoginActivity loginActivity;
    private boolean isMobileNoOK = false;
    private boolean isPassOK = false;
    private boolean isConfirmPassOK = false;
    Handler mHandler;
    Utils mUtils;

    @BindView(R.id.input_mobile)
    EditText mMobileNo;

    @BindView(R.id.input_password)
    EditText mPassword;

    @BindView(R.id.input_confirm_password)
    EditText mConfirmPassword;

    @BindView(R.id.btn_signup)
    AppCompatButton mRegister;

    @BindView(R.id.clRootRegister)
    ScrollView mRootWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        validateWidgets();
    }

    public void validateWidgets() {
        mMobileNo.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
        mConfirmPassword.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        View view = loginActivity.getCurrentFocus();
        if (view != null) {
            switch (view.getId()) {
                case R.id.input_mobile:
                    validateMobileNo(s);
                    break;
                case R.id.input_password:
                    validatePass(s);
                    break;
                case R.id.input_confirm_password:
                    validateConfirmPass(s);
                    break;
            }
        }
    }

    public void validateMobileNo(Editable s) {
        isMobileNoOK = s.length() == 10;
        setError(isMobileNoOK, mMobileNo, "Please, Enter Valid Mobile No.");
        setBnRegister();
    }

    public void validatePass(Editable s) {
        isPassOK = !TextUtils.isEmpty(s);
        setError(isPassOK, mPassword, "Please, Enter Password Properly.");
        setBnRegister();
    }

    public void validateConfirmPass(Editable s) {
        isConfirmPassOK = mPassword.getText().toString().equals(s.toString());
        setError(isConfirmPassOK, mConfirmPassword, "Password & Confirm Password should be same.");
        setBnRegister();
    }

    public void setBnRegister() {
        mRegister.setEnabled((isMobileNoOK
                && isPassOK
                && isConfirmPassOK
        ));
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


    @OnClick(R.id.btn_signup)
    public void onRegister() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mUtils.isDeviceOnline(loginActivity)) {
                    new DoRegister(
                            mMobileNo.getText().toString(),
                            mPassword.getText().toString()
                    ).execute();
                } else {
                    Snackbar.make(mRootWidget,
                            getString(R.string.device_offline),
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    class DoRegister extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        String mobileNo;
        String password;

        public DoRegister(String mobileNo, String pass) {
            super();
            dialog = new ProgressDialog(loginActivity);
            this.mobileNo = mobileNo;
            this.password = pass;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Registering...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String, String> pairs = new HashMap<>();
            pairs.put("user", mobileNo);
            pairs.put("passwd", password);
            try {
                return mUtils.postToServer(AppConstants.Url.BASE_URL + AppConstants.Url.REGISTER_USER, pairs,"");
            } catch (Exception e) {
                Log.e(TAG, "DoLogin::doInBackground(): " + e.getMessage());
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
                Log.e(TAG, "DoRegister::onPostExecute(): result is: " + result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj != null && jsonObj.getInt("responseCode") == 200 && jsonObj.getString("data") != null && jsonObj.getString("data").equals("SUCCESS")) {
                        mUtils.showMessage(
                                loginActivity,
                                "Success",
                                "Registration Successful.",
                                "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getFragmentManager().popBackStackImmediate();
                                    }
                                }
                        );
                    } else {
                        mUtils.showMessage(
                                loginActivity,
                                "Error",
                                "Registration Fail.\nUser already present",
                                "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e(TAG, "DoRegister::onPostExecute(): unexpected server result");
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
