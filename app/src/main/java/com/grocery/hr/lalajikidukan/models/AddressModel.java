package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 17/4/17.
 */

public class AddressModel {

    String user;
    String name;
    int pincode;
    String landmark;
    String flat;
    String locality;
    String addressType;
    int id;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", pincode=" + pincode +
                ", landmark='" + landmark + '\'' +
                ", flat='" + flat + '\'' +
                ", locality='" + locality + '\'' +
                ", addressType='" + addressType + '\'' +
                ", id=" + id +
                '}';
    }
}
