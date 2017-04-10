package com.grocery.hr.lalajikidukan.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.grocery.hr.lalajikidukan.configuration.CloudinaryConfiguration;

/**
 * Created by vipul on 5/4/17.
 */

public class CloudinaryUtility {

    public static String getRoundCornerImageUrl(int radius,String image){
        Cloudinary cloudinary=new Cloudinary(CloudinaryConfiguration.getCloudinaryConfig());
        //Manipulation
        Transformation t=new Transformation();
        return  cloudinary.url().transformation(t).generate(image);
    }

    public static String getResizeImageUrl(int width,int height,String image){
        Cloudinary cloudinary=new Cloudinary(CloudinaryConfiguration.getCloudinaryConfig());
        Transformation t=new Transformation();
        t.width(width);
        t.height(height);
        return  cloudinary.url().transformation(t).generate(image);
    }

    public static String getImageUrl(String image){
        Cloudinary cloudinary=new Cloudinary(CloudinaryConfiguration.getCloudinaryConfig());
        return cloudinary.url().generate(image);
    }

}
