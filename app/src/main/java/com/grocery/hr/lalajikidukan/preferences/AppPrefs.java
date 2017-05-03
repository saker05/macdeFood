package com.grocery.hr.lalajikidukan.preferences;

/**
 * Created by vipul on 26/3/17.
 */

public class AppPrefs {

    public static final String TAG = AppPrefs.class.getSimpleName();

    private  String LastLocation = null;
    private  Boolean running = false;
    private  int  activityDrawerItemPosition=0;
    private  boolean isHighligherAutoSwiperThreadIsRunning=false;

    private static AppPrefs instance;

    private AppPrefs(){

    }

    public int getActivityDrawerItemPosition() {
        return activityDrawerItemPosition;
    }

    public void setActivityDrawerItemPosition(int activityDrawerItemPosition) {
        this.activityDrawerItemPosition = activityDrawerItemPosition;
    }

    public static AppPrefs getInstance(){
        if(instance==null){
            instance=new AppPrefs();
        }
        return instance;
    }

    public String getLastLocation() {
        return LastLocation;
    }

    public Boolean getRunning() {
        return running;
    }

    public boolean isHighligherAutoSwiperThreadIsRunning() {
        return isHighligherAutoSwiperThreadIsRunning;
    }

    public void setHighligherAutoSwiperThreadIsRunning(boolean highligherAutoSwiperThreadIsRunning) {
        isHighligherAutoSwiperThreadIsRunning = highligherAutoSwiperThreadIsRunning;
    }

    public void setLastLocation(String lastLocation) {
        LastLocation = lastLocation;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

}
