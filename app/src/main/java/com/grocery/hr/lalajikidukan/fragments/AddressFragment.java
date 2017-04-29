package com.grocery.hr.lalajikidukan.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.LoginActivity;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 17/4/17.
 */

public class AddressFragment extends Fragment {

    public static final String TAG = AddressFragment.class.getSimpleName();

    private Utils mUtils;
    private List<AddressModel> addresses;
    private MainActivity mActivity;
    private Handler mHandler;
    private String type;
    private AddressAdapter mAdapter;

    Toolbar mToolbar;

    @BindView(R.id.text_label)
    TextView mLabel;

    @BindView(R.id.rv_address)
    SuperRecyclerView mAddressList;

    @BindView(R.id.rl_root)
    RelativeLayout mRootWidget;

    @BindView(R.id.ll_spinner)
    LinearLayout spinner;


    public AddressFragment(String type) {
        this.type = type;
    }

    public static AddressFragment newInstance(String type) {
        return new AddressFragment(type);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mUtils = Utils.getInstance();
        mAdapter = new AddressAdapter();
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
            return inflater.inflate(R.layout.fragment_address, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.hideCart();

        if (!mUtils.isUserLoggedIn(getContext())) {
            loadLoginActivity();
        }

        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
            mAddressList.getProgressView().setVisibility(View.GONE);
            mToolbar = (Toolbar) getActivity().findViewById(R.id.addresstb);
            setUpToolbar();
            setUpViews();
        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            setUpToolbar();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.showCart();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

        if (type == AppConstants.Address_Fragment.CHECKOUT) {
            mActivity.setTitle(getString(R.string.action_choose_address));
        } else if (type == AppConstants.Address_Fragment.MY_ADDRESSES) {
            mActivity.setTitle(getString(R.string.action_my_address));
        }
    }

    public void loadLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        if (mActivity.getParent() == null) {
            startActivityForResult(intent, 2);
        } else {
            mActivity.getParent().startActivityForResult(intent, 2);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_CANCELED && (AppConstants.Address_Fragment.MY_ADDRESSES).equals(type)) {
            mActivity.clearBackStack();
            mActivity.loadFragment(0);
        } else if (resultCode == mActivity.RESULT_CANCELED && (AppConstants.Address_Fragment.CHECKOUT).equals(type)) {
            mActivity.onBackPressed();
        } else {
            baseGetAddresses();
        }

    }

    public void setUpViews() {
        if (type == AppConstants.Address_Fragment.CHECKOUT) {
            mLabel.setText("Delivering in");
        } else if (type == AppConstants.Address_Fragment.MY_ADDRESSES) {
            mLabel.setText("Saved Addresses");
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAddressList.setLayoutManager(linearLayoutManager);

        if (mUtils.isUserLoggedIn(getContext())) {
            mAddressList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    baseGetAddresses();
                }
            });
            baseGetAddresses();
        }

    }

    public void baseGetAddresses() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetAddress().execute();
            }
        });
    }

    @OnClick(R.id.linear12)
    public void addAddressOnClick() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContentMain, EditOrAddAddressFragment
                                .newInstance(),
                        EditOrAddAddressFragment.TAG).addToBackStack(null)
                .commit();
    }


    class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {

        @Override
        public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AddressViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_address, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(AddressViewHolder holder, int position) {
            AddressModel address = addresses.get(position);
            holder.getAddressType().setText(address.getAddressType());
            holder.getNameAddress().setText(address.getName());
            holder.getLandmarkAddress().setText(address.getLandmark());
            holder.getFlatLocalityAddress().setText(address.getFlat() + "," + address.getLocality() + " " + address.getPincode());
        }

        @Override
        public int getItemCount() {
            if (addresses == null) {
                return 0;
            } else {
                return addresses.size();
            }
        }
    }

    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_address_type)
        TextView addressType;

        @BindView(R.id.text_name)
        TextView nameAddress;

        @BindView(R.id.text_flat_locality)
        TextView flatLocalityAddress;

        @BindView(R.id.text_landmark)
        TextView landmarkAddress;


        public AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick(R.id.editoption)
        public void onIconClick() {
            View menuItemView = mActivity.findViewById(R.id.editoption);
            PopupMenu popup = new PopupMenu(getActivity(), menuItemView);
            MenuInflater inflater = popup.getMenuInflater();
            mActivity.getMenuInflater().inflate(R.menu.address_popmenu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            AddressModel addressModel = addresses.get(getAdapterPosition());
                            mActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.flContentMain, EditOrAddAddressFragment
                                                    .newInstance(addressModel),
                                            EditOrAddAddressFragment.TAG).addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.menu_delete:
                            // ....
                            return true;

                    }
                    return true;
                }
            });
            popup.show();
        }



       /* @OnClick(R.id.menu_edit)
        public void onEditClick(){
            AddressModel addressModel=addresses.get(getAdapterPosition());
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, EditOrAddAddressFragment
                                    .newInstance(addressModel),
                            EditOrAddAddressFragment.TAG).addToBackStack(null)
                    .commit();
        }*/


        public TextView getAddressType() {
            return addressType;
        }

        public TextView getNameAddress() {
            return nameAddress;
        }

        public TextView getFlatLocalityAddress() {
            return flatLocalityAddress;
        }

        public TextView getLandmarkAddress() {
            return landmarkAddress;
        }

        @Override
        public void onClick(View v) {
            if (AppConstants.Address_Fragment.CHECKOUT.equals(type)) {

            }
        }
    }


    class GetAddress extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mUtils.getFromServer(AppConstants.Url.BASE_URL
                        + AppConstants.Url.GET_ADDRESS, mUtils.getUerPasswordMap(getContext()));
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
                addresses = JsonParserUtils.addressParser(result);
                mAddressList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                try {
                    Snackbar.make(mRootWidget,
                            getString(R.string.cant_connect_to_server),
                            Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            spinner.setVisibility(View.GONE);
        }
    }


}
