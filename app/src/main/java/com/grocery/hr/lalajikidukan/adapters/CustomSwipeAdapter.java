package com.grocery.hr.lalajikidukan.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.models.ProductModel;

import java.util.List;

/**
 * Created by Rohit on 21-04-2017.
 */

public class CustomSwipeAdapter extends PagerAdapter
{
    private int [] image_resource ={R.drawable.corbeille_de_fruits_en_bois,R.drawable.discount15,R.drawable.offer15,R.drawable.pic3};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<ProductModel> highlightedProductItems;

     public CustomSwipeAdapter(Context ctx,List<ProductModel> highlightedProductItems)
     {
         this.ctx=ctx;
         this.highlightedProductItems=highlightedProductItems;
     }



    @Override
    public int getCount() {
        if(highlightedProductItems!=null){
            return highlightedProductItems.size();
        }
        return 0;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = layoutInflater.inflate(R.layout.customswipe,container,false);
        ImageView imageView=(ImageView)itemview.findViewById(R.id.customswipeimage);
        imageView.setImageResource(image_resource[position]);
        container.addView( itemview );
        return itemview;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout) object);
    }
}
