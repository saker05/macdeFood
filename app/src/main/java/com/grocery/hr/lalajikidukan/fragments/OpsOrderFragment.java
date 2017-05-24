package com.grocery.hr.lalajikidukan.fragments;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.OpsOrderDetailModel;
import com.grocery.hr.lalajikidukan.models.*;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpsOrderFragment extends Fragment {

    public static final String TAG = OpsOrderFragment.class.getSimpleName();

    private OpsOrderFragment(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private OpsOrderDetailAdapter mAdapter;
    private MainActivity mActivity;
    private Utils mUtils;
    private Handler mHandler;
    private OpsOrderModel opsOrderModel;
    private List<OpsOrderDetailModel> opsOrderDetailModelList;
    private String orderStatus;
    private int offset;
    private int size;

    private Toolbar mToolbar;

    @BindView(R.id.rv_ops_order)
    SuperRecyclerView mSRecyclerView;

    @BindView(R.id.ll_root)
    LinearLayout mRootWidget;

    public static OpsOrderFragment newInstance(String orderStatus) {
        return new OpsOrderFragment(orderStatus);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        mAdapter=new OpsOrderDetailAdapter();
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
            return inflater.inflate(R.layout.fragment_ops_order, container, false);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.hideCart();
        if (mUtils.isDeviceOnline(getContext())) {
            ButterKnife.bind(this, view);
            //mToolbar = (Toolbar) getActivity().findViewById(R.id.cartToolbar);
            setUpToolbar();
            setUpViews();
        } else {
            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            setUpToolbar();
        }
    }


    public void setUpToolbar() {
    }


    public void setUpViews() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSRecyclerView.setLayoutManager(linearLayoutManager);
        mSRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseGetOpsOrder();
            }
        });
    }


    class OpsOrderDetailAdapter extends RecyclerView.Adapter<OrderDetailViewHolder> {
        @Override
        public OrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderDetailViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_ops_order, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(OrderDetailViewHolder holder, int position) {
            UserOrderModel userOrderModel = opsOrderDetailModelList.get(position).getUserOrderModel();
            AddressModel addressModel = opsOrderDetailModelList.get(position).getAddressModel();
            holder.getMAddress().setText(addressModel.getLocality() + " " + addressModel.getPhoneNumber());
            holder.getMTime().setText(userOrderModel.getOrderDate());
            holder.getMOrderId().setText(userOrderModel.getUoc());
            holder.getMOrderStatus().setText(userOrderModel.getStatus().toString());
            setRadioText(holder);
        }

        @Override
        public int getItemCount() {
            if (opsOrderDetailModelList != null) {
                return opsOrderDetailModelList.size();
            }
            return 0;
        }
    }


    class OrderDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_time)
        TextView mTime;

        @BindView(R.id.text_date)
        TextView mOrdeDate;

        @BindView(R.id.text_id)
        TextView mOrderId;

        @BindView(R.id.text_address)
        TextView mAddress;

        @BindView(R.id.text_status)
        TextView mOrderStatus;

        @BindView(R.id.radio_0)
        RadioButton mRadioText1;

        @BindView(R.id.radio_1)
        RadioButton mRadioText2;

        @BindView(R.id.button_move)
        Button mMoveButton;

        @BindView(R.id.radio_group)
        RadioGroup radioGroup;


        public OrderDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public TextView getMTime() {
            return mTime;
        }

        public TextView getMOrdeDate() {
            return mOrdeDate;
        }

        public TextView getMOrderId() {
            return mOrderId;
        }

        public TextView getMAddress() {
            return mAddress;
        }

        public TextView getMOrderStatus() {
            return mOrderStatus;
        }

        public RadioButton getRadioText1(){return mRadioText1; }

        public RadioButton getRadioText2(){return mRadioText2; }

        public Button getMoveButton(){return mMoveButton;}


        @Override
        public void onClick(View v) {
            AddressModel addressModel = opsOrderDetailModelList.get(getAdapterPosition()).getAddressModel();
            UserOrderModel userOrderModel = opsOrderDetailModelList.get(getAdapterPosition()).
                    getUserOrderModel();
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContentMain, OpsSuborderDescriptionFragment.getInstance(
                            addressModel, userOrderModel), OpsSuborderDescriptionFragment.TAG).
                    addToBackStack(null)
                    .commit();
        }

        @OnClick(R.id.button_move)
        public void onMoveButtonClick(){
            UserOrderModel userOrderModel = opsOrderDetailModelList.get(getAdapterPosition()).getUserOrderModel();
            final String[] params=new String[2];
            params[0]=userOrderModel.getUoc();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.radio_0:
                            params[1]=mRadioText1.getText().toString();
                            new UpdateOrderStatus().execute(params);
                            opsOrderDetailModelList.remove(getAdapterPosition());
                            mAdapter.notifyDataSetChanged();
                        case R.id.radio_1:
                            params[1]=mRadioText2.getText().toString();
                            new UpdateOrderStatus().execute(params);
                            opsOrderDetailModelList.remove(getAdapterPosition());
                            mAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            });
        }
    }

    public void baseGetOpsOrder() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetOpsOrder().execute();

            }
        });
    }


    class GetOpsOrder extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String opsOrderUrl = AppConstants.Url.GET_OPS_ORDER_DETAIL;
                opsOrderUrl = opsOrderUrl.replace("+", String.valueOf(offset));
                opsOrderUrl = opsOrderUrl.replace("%", String.valueOf(offset + size));
                opsOrderUrl = opsOrderUrl.replace("#", orderStatus);
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + opsOrderUrl, mUtils.getUerPasswordMap(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCategories::onGetExecte(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {
                    opsOrderModel = JsonParserUtils.opsOrdeParser(result);
                    opsOrderDetailModelList = opsOrderModel.getOpsOrderDetailModel();
                    mSRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server));
            }
            // mSpinner.setVisibility(View.GONE);
        }
    }

    class UpdateOrderStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String opsOrderUrl = AppConstants.Url.UPDATE_ORDER_STATUS;
                opsOrderUrl = opsOrderUrl.replace("+", params[0]);
                opsOrderUrl = opsOrderUrl.replace("#", params[1]);
                return mUtils.putToServer(AppConstants.Url.BASE_URL + opsOrderUrl, mUtils.getUerPasswordMap(getContext()),"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetCategories::onGetExecte(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                BaseResponse baseResponse = JsonParserUtils.getBaseResponse(result);
                if (baseResponse != null && baseResponse.getResponseCode() == 403) {
                    showSnackbar(getString(R.string.forbidden));
                } else if (baseResponse != null && baseResponse.getResponseCode() >= 400 &&
                        baseResponse.getResponseCode() < 500) {
                    showSnackbar(baseResponse.getResponseMessage() + " " + getString(R.string.complaint_to_admin));
                } else {

                }
            } else {
                showSnackbar(getString(R.string.cant_connect_to_server)+"\n Your order status not been updated");
            }
        }
    }

    private void showSnackbar(String message) {
        try {
            Snackbar.make(mRootWidget, message,
                    Snackbar.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRadioText(OrderDetailViewHolder holder){
        switch (orderStatus){
            case AppConstants.OrderStatus.PLACED:
                holder.getRadioText1().setText(AppConstants.OrderStatus.PREPARING);
                break;

            case AppConstants.OrderStatus.PREPARING:
                holder.getRadioText1().setText(AppConstants.OrderStatus.DISPATCHED);
                break;

            case AppConstants.OrderStatus.DISPATCHED:
                holder.getRadioText1().setText(AppConstants.OrderStatus.DELIVERED);
                break;

            case AppConstants.OrderStatus.DELIVERED:
                holder.getRadioText1().setVisibility(View.GONE);
                holder.getRadioText2().setVisibility(View.GONE);
                holder.getMoveButton().setVisibility(View.GONE);
                break;

            case AppConstants.OrderStatus.REJECTED:
                holder.getRadioText1().setText(AppConstants.OrderStatus.PLACED);
                holder.getRadioText2().setVisibility(View.GONE);
                break;
        }
    }


}
