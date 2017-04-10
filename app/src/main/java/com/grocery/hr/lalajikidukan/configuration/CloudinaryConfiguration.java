package com.grocery.hr.lalajikidukan.configuration;

import com.grocery.hr.lalajikidukan.constants.AppConstants;

import java.util.HashMap;

/**
 * Created by vipul on 5/4/17.
 */

public class CloudinaryConfiguration {

    public static HashMap getCloudinaryConfig(){
        HashMap config=new HashMap();
        config.put(AppConstants.Cloudinary.CLOUD_NAME,AppConstants.Cloudinary.CLOUD_NAME_VALUE);
        config.put(AppConstants.Cloudinary.API_KEY,AppConstants.Cloudinary.API_KEY_VALUE);
        config.put(AppConstants.Cloudinary.API_SECRET,AppConstants.Cloudinary.API_SECRET_VALUE);

        return config;
    }
}
