package com.grocery.hr.lalajikidukan.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.service.CartService;
import com.grocery.hr.lalajikidukan.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 18/4/17.
 */

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    LoginActivity loginActivity;

    @BindView(R.id.etLoginMobileNo)
    EditText mobileNo;

    @BindView(R.id.etLoginPass)
    EditText password;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity=(LoginActivity)getActivity();
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
        View abc=inflater.inflate(R.layout.fragment_login, container, false);
        return abc;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.txt_signup)
    public void onClickSignup(){
        loginActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.logincontent, SignupFragment.newInstance(),
                        SignupFragment.TAG).addToBackStack(null)
                .commit();
    }


    @OnClick(R.id.bnLogin)
    public void onClickSignin(){

    }


}



