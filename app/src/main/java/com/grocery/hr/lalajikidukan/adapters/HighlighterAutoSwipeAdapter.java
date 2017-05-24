package com.grocery.hr.lalajikidukan.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.fragments.HomeFragment;
import com.grocery.hr.lalajikidukan.fragments.ProductDescriptionFragment;
import com.grocery.hr.lalajikidukan.manager.PicassoManager;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.utils.CloudinaryUtility;

import java.util.List;

/**
 * Created by Rohit on 21-04-2017.
 */

public class HighlighterAutoSwipeAdapter extends PagerAdapter {
    //private int [] image_resource ={R.drawable.capture1,R.drawable.capture2,R.drawable.capture3,R.drawable.capture4};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<ProductModel> highlightedProductItems;
    private PicassoManager picassoManager;
    private HomeFragment homeFragment;

    public HighlighterAutoSwipeAdapter(Context ctx, List<ProductModel> highlightedProductItems) {
        this.ctx = ctx;
        this.highlightedProductItems = highlightedProductItems;
        picassoManager = PicassoManager.getInstance();
        homeFragment = HomeFragment.newInstance();
    }

    @Override
    public int getCount() {
        if (highlightedProductItems != null) {
            return highlightedProductItems.size();
        }
        return 0;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = layoutInflater.inflate(R.layout.item_view_highlighters, container, false);
        String imageUrl = CloudinaryUtility.getResizeImageUrl(500, 200,
                highlightedProductItems.get(position).getImageUrl());
        ImageView imageView = (ImageView) itemview.findViewById(R.id.image);
        picassoManager.downloadImage(ctx, imageUrl, imageView);
        container.addView(itemview);
        itemview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity) ctx).getSupportFragmentManager().beginTransaction().replace(
                        R.id.flContentMain, ProductDescriptionFragment.newInstance(highlightedProductItems.get(position))
                        , ProductDescriptionFragment.TAG).addToBackStack(null).commit();
            }
        });
        return itemview;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
