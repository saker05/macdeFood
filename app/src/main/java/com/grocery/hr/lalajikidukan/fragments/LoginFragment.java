package com.grocery.hr.lalajikidukan.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.LoginActivity;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.service.CartService;
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

import static android.R.attr.data;

/**
 * Created by vipul on 18/4/17.
 */

public class LoginFragment extends Fragment implements TextWatcher {

    public static final String TAG = LoginFragment.class.getSimpleName();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    LoginActivity loginActivity;
    Utils mUtils;
    boolean isMobileNoOK = false;
    boolean isPassOK = false;
    Handler mHandler;

    @BindView(R.id.clRootLogin)
    CoordinatorLayout mRootWidget;

    @BindView(R.id.etLoginMobileNo)
    EditText mMobileNo;

    @BindView(R.id.etLoginPass)
    EditText mPassword;

    @BindView(R.id.bnLogin)
    AppCompatButton mLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
        mUtils = Utils.getInstance();
        mHandler = new Handler();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        // mUtils.setWidgetTint(mRootWidget, mActivity);
        validateWidgets();
    }

    public void validateWidgets() {
        mMobileNo.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
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
        if (null != view) {
            switch (view.getId()) {
                case R.id.etLoginMobileNo:
                    validateMobileNo(s);
                    break;
                case R.id.etLoginPass:
                    validatePass(s);
                    break;
            }
        }
    }

    public void validateMobileNo(Editable s) {
        isMobileNoOK = s.length() == 10;
        setError(isMobileNoOK, mMobileNo, "Please, Enter Valid Mobile No.");
        setBnLogin();
    }

    public void validatePass(Editable s) {
        isPassOK = !TextUtils.isEmpty(s);
        setBnLogin();
    }

    public void setBnLogin() {
        mLogin.setEnabled((isMobileNoOK && isPassOK));
    }

    @OnClick(R.id.txt_signup)
    public void onClickSignup() {
        loginActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.logincontent, SignupFragment.newInstance(),
                        SignupFragment.TAG).addToBackStack(null)
                .commit();
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

    @OnClick(R.id.bnLogin)
    public void onClickSignin() {
        mUtils.hideSoftKeyboard(loginActivity);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mUtils.isDeviceOnline(loginActivity)) {
                    new DoLogin().execute();
                } else {
                    Snackbar.make(mRootWidget, getString(R.string.device_offline), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    class DoLogin extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        String mobileNo;
        String password;

        public DoLogin() {
            super();
            dialog = mUtils.getProgressDialog(loginActivity);
            mobileNo = mMobileNo.getText().toString();
            password = mPassword.getText().toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String, String> pairs = new HashMap<>();
            pairs.put("user", mobileNo);
            pairs.put("passwd", password);
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + AppConstants.Url.LOGIN_USER, pairs);
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
                Log.e(TAG + " result", result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj != null && jsonObj.getInt("responseCode") == 200 && jsonObj.getString("data")!=null) {
                        AppSharedPreference.putString(getContext(), AppConstants.User.MOBILE_NO, mobileNo);
                        AppSharedPreference.putString(getContext(), AppConstants.User.PASSWORD, password);
                        AppSharedPreference.putString(getContext(),AppConstants.User.TYPE,jsonObj.getString("data"));
                        loginActivity.finish();
                    } else {
                        mUtils.showMessage(
                                loginActivity,
                                "Error",
                                "Login Fail.\nTry Again",
                                "OK", null
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
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



