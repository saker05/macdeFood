package com.grocery.hr.lalajikidukan.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.grocery.hr.lalajikidukan.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by vipul on 5/4/17.
 */

public class PicassoManager {


    private static PicassoManager picassoManagerInstance = new PicassoManager();

    private PicassoManager() {

    }

    public static PicassoManager getInstance() {
        return picassoManagerInstance;
    }


    public void downloadImage(Context c, String url, ImageView img) {
        if (url != null && url.length() > 0) {
            Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img);
        } else {
            Picasso.with(c).load(R.drawable.placeholder).into(img);
        }
    }

   /* public Bitmap getbitmapOfImage(Context c, String url){
        final Bitmap[] imageBitMap = new Bitmap[1];
        try {
            Picasso.with(c).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageBitMap[0] =bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    //Here you should place a loading gif in the ImageView to
                    //while image is being obtained.
                }
            });
        }catch (Exception e){
            Log.e("afd","adsf");
        }
        return imageBitMap[0];
    }
*/
    public Bitmap getbitmapOfImage(final Context c, final String url){
        final Bitmap[] imageBitMap = new Bitmap[1];
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                try {
                    Picasso.with(c).load(url).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageBitMap[0] =bitmap;
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            //Here you should place a loading gif in the ImageView to
                            //while image is being obtained.
                        }
                    });
                }catch (Exception e){
                    Log.e("afd","adsf");
                }
            }
        });

         Log.i("a","");
        return imageBitMap[0];
    }



}
