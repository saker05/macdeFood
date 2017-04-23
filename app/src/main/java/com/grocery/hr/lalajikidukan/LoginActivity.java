package com.grocery.hr.lalajikidukan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.grocery.hr.lalajikidukan.fragments.LoginFragment;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1234;
    private static final int RESULT_CODE = 5;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler=new Handler();
        ButterKnife.bind(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setUpView();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getParent() == null) {
            setResult(Activity.RESULT_CANCELED);
        } else {
            getParent().setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    public void setUpView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.logincontent, LoginFragment
                                .newInstance(),
                        LoginFragment.TAG)
                .commit();
    }

}

