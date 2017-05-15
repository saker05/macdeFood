package com.grocery.hr.lalajikidukan.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.OpsOrderDetailModel;
import com.grocery.hr.lalajikidukan.models.*;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpsOrderFragment extends Fragment {

    private OrderDetailAdapter madapter;
    private MainActivity mActivity;
    Toolbar mToolbar;
    Utils mUtils;
    Handler mHandler;
    OpsOrderModel opsOrderModel;
    List<OpsOrderDetailModel> opsOrderDetailModelList;

    public static final String TAG = OpsOrderFragment.class.getSimpleName();

    @BindView(R.id.rv_ops_order)
    RecyclerView mrecyclerView;

    @BindView(R.id.ll_root)
    LinearLayout mRootWidget;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mActivity = (MainActivity) getActivity();
        mHandler = new Handler();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ops_order, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setUp();


    }


    public void setUp() {

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(linearLayoutManager2);
        madapter = new OrderDetailAdapter();
        mrecyclerView.setAdapter(madapter);

        baseGetOpsOrder();

    }


    class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailViewHolderr> {
        @Override
        public OrderDetailViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderDetailViewHolderr(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_ops_order, parent, false)
            );
        }


        @Override
        public void onBindViewHolder(OrderDetailViewHolderr holder, int position) {
            UserOrderModel userOrderModel = opsOrderDetailModelList.get(position).getUserOrderModel();
            AddressModel addressModel = opsOrderDetailModelList.get(position).getAddressModel();
            holder.getMAddress().setText(addressModel.getLocality() + " " + addressModel.getPhoneNumber());
            holder.getMTime().setText(userOrderModel.getOrderDate());
            holder.getMOrderId().setText(userOrderModel.getUoc());
            holder.getMOrderStatus().setText(userOrderModel.getStatus().toString());
        }


        @Override
        public int getItemCount() {
            if (opsOrderDetailModelList != null) {
                return opsOrderDetailModelList.size();
            }
            return 0;
        }
    }


    class OrderDetailViewHolderr extends RecyclerView.ViewHolder implements View.OnClickListener {


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


        public OrderDetailViewHolderr(View itemView) {
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

    }


    public void baseGetOpsOrder() {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetCategories().execute();

            }
        });
    }


    class GetCategories extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String opsOrderUrl= AppConstants.Url.GET_OPS_ORDER_DETAIL;
                opsOrderUrl = opsOrderUrl.replace("+","1");
                opsOrderUrl = opsOrderUrl.replace("%","3");
                opsOrderUrl = opsOrderUrl.replace("#","PLACED");
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + opsOrderUrl,mUtils.getUerPasswordMap(getContext()));

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
                opsOrderModel = JsonParserUtils.opsOrdeParser(result);
                opsOrderDetailModelList = opsOrderModel.getOpsOrderDetailModel();
                mrecyclerView.setAdapter(madapter);
                madapter.notifyDataSetChanged();
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
            // mSpinner.setVisibility(View.GONE);
        }
    }


}
