package com.grocery.hr.lalajikidukan.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.models.UserOrderModel;
import com.grocery.hr.lalajikidukan.models.UserSubOrderModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpsSuborderDescriptionFragment extends Fragment {



    AddressModel addressModel;
    UserOrderModel userOrderModel;
    List<UserSubOrderModel> userSubOrderModelItems;
    OrderBillDeescriptionAdapter mAdapter;


    public static final String TAG = OpsSuborderDescriptionFragment.class.getSimpleName();

    private OpsSuborderDescriptionFragment(AddressModel addressModel, UserOrderModel userOrderModel){
        this.addressModel=addressModel;
        this.userOrderModel=userOrderModel;
    }

    public static OpsSuborderDescriptionFragment getInstance(AddressModel addressModel, UserOrderModel userOrderModel){
        return new OpsSuborderDescriptionFragment(addressModel,userOrderModel);
    }

    private MainActivity mActivity;
    Toolbar mToolbar;
    Utils mUtils;
    Handler mHandler;




    @BindView(R.id.rv_suborder_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.cl_root)
    CoordinatorLayout mRootWidget;

    public OpsSuborderDescriptionFragment() {
        // Required empty public constructor
    }

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ops_suporder_description, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupViews();



    }


    public void setUpToolbar() {


        mActivity.setSupportActionBar(mToolbar);
        mActivity.setTitle("Order Description");


    }



    public void setupViews()
    {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new OrderBillDeescriptionAdapter();
        mRecyclerView.setAdapter(mAdapter);
        baseGetOpsOrder();

        //baseGetOpsOrder();

    }









    class OrderBillDeescriptionAdapter extends RecyclerView.Adapter<OrderBillDescritionViewHolderr> {
        @Override
        public OrderBillDescritionViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderBillDescritionViewHolderr(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_ops_suborder_description, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(OrderBillDescritionViewHolderr holder, int position)
        {
            UserSubOrderModel userSubOrderModel = userSubOrderModelItems.get(position);

            holder.getmFoodItem().setText(userSubOrderModel.getUnitAmount()+"*"+userSubOrderModel.getNoOfUnits());
            holder.getmFoodQuantity().setText(userSubOrderModel.getNoOfUnits()+"*"+userSubOrderModel.getUnitQuantityInGm());
            holder.getmOrderDescription().setText(userSubOrderModel.getName());
        }





        @Override
        public int getItemCount() {
            if(userSubOrderModelItems!=null)
                return userSubOrderModelItems.size();
            else
                return 0;
        }
    }


    class OrderBillDescritionViewHolderr extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        @BindView(R.id.text_description)
        TextView mOrderDescription;

        @BindView(R.id.food_item)
        TextView mFoodItem;

        @BindView(R.id.food_quantity)
        TextView mFoodQuantity;




        public OrderBillDescritionViewHolderr(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getmOrderDescription()
        {
            return mOrderDescription;
        }

        public TextView getmFoodItem()
        {
            return mFoodItem;
        }

        public TextView getmFoodQuantity()
        {
            return mFoodQuantity;
        }



        @Override
        public void onClick(View v)
        {

        }
    }


    public void baseGetOpsOrder() {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new GetSubOrders().execute();

            }
        });
    }




    class GetSubOrders extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String subOrderUrl= AppConstants.Url.GET_SUB_ORDERS;
                subOrderUrl = subOrderUrl.replace("?",userOrderModel.getUoc());
                return mUtils.getFromServer(AppConstants.Url.BASE_URL + subOrderUrl, mUtils.getUerPasswordMap(getContext()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetSubOrders::onGetExecte(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                userSubOrderModelItems = JsonParserUtils.subOrderParser(result);
                mRecyclerView.setAdapter(mAdapter);
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
            //mSpinner.setVisibility(View.GONE);
        }
    }



}
