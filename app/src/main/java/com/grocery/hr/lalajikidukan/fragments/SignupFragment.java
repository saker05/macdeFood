package com.grocery.hr.lalajikidukan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.grocery.hr.lalajikidukan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul on 18/4/17.
 */

public class SignupFragment extends Fragment {

    public static final String TAG = SignupFragment.class.getSimpleName();

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @BindView(R.id.input_mobile)
    EditText mobileNo;

    @BindView(R.id.input_password)
    EditText password;

    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }


}
