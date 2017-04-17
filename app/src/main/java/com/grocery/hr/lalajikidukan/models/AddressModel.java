package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 17/4/17.
 */

public class AddressModel {

    String user;
    String name;
    int pincode;
    String city;
    String state;
    String address;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressModel [user=" + user + ", name=" + name + ", pincode=" + pincode + ", city=" + city
                + ", state=" + state + ", address=" + address + ", id=" + id + "]";
    }

}
