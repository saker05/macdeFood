package com.grocery.hr.lalajikidukan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.manager.PicassoManager;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.utils.CloudinaryUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul on 18/4/17.
 */

public class ProductDescriptionFragment extends Fragment {

    public static final String TAG = ProductDescriptionFragment.class.getSimpleName();

    private ProductModel productItem;
    private MainActivity mActivity;
    private PicassoManager picassoManager;
    private CartManager cartManager;

    @BindView(R.id.text_description)
    TextView mDescritpion;

    @BindView(R.id.text_unit_quantity)
    TextView mUnit;

    @BindView(R.id.text_price)
    TextView mPrice;

    @BindView(R.id.text_name)
    TextView mName;

    @BindView(R.id.text_disclaimer)
    TextView mDisclaimer;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.image_plus)
    ImageView mPlus;

    @BindView(R.id.image_minus)
    ImageView mMinus;

    @BindView(R.id.text_units_in_cart)
    TextView mUnitInCart;


    public  ProductDescriptionFragment(ProductModel productItem) {
        this.productItem = productItem;
    }

    public static ProductDescriptionFragment newInstance(ProductModel product) {
        return new ProductDescriptionFragment(product);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        picassoManager=PicassoManager.getInstance();
        cartManager = CartManager.getInstance(getContext());
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
        return inflater.inflate(R.layout.fragment_product_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpToolbar();
        setUpView();
    }

    public void setUpToolbar() {
       /* mActivity.setSupportActionBar(mToolbar);
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
        });*/
    }

    public void setUpView(){
        if (productItem.getUnitQuantityInGm() == 0) {
            mUnit.setText("1 unit");
        } else {
            mUnit.setText(String.valueOf(productItem.getUnitQuantityInGm()) + "gm");
        }

        mDescritpion.setText(productItem.getDescription());
        mPrice.setText(String.valueOf(productItem.getUnitAmount()));
        mName.setText(productItem.getName());
        String imageUrl = CloudinaryUtility.getResizeImageUrl(600, 600, productItem.getImageUrl());
        picassoManager.downloadImage(getContext(), imageUrl,image);
        showCartCount();
    }

    @OnClick(R.id.image_plus)
    public void onPlusClick() {
        cartManager.insertByOne(productItem.getUpc());
        productItem.setNoOfItemInCart(productItem.getNoOfItemInCart() + 1);
        mActivity.showCart();
        showCartCount();
    }

    @OnClick(R.id.image_minus)
    public void onMinusClick() {
        cartManager.removeByOne(productItem.getUpc());
        if (productItem.getNoOfItemInCart() > 0) {
            productItem.setNoOfItemInCart(productItem.getNoOfItemInCart() - 1);
        }
        mActivity.showCart();
        showCartCount();
    }

    private void showCartCount(){
        if (productItem.getNoOfItemInCart() == 0) {
            mMinus.setVisibility(View.GONE);
            mUnitInCart.setVisibility(View.GONE);
        } else {
            mMinus.setVisibility(View.VISIBLE);
            mPlus.setVisibility(View.VISIBLE);
            mUnitInCart.setText(String.valueOf(productItem.getNoOfItemInCart()));
        }
    }
}
