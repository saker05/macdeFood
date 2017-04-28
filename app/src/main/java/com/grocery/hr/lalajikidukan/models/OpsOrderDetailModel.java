package com.grocery.hr.lalajikidukan.models;

/**
 * Created by Rohit on 25-04-2017.
 */
public class OpsOrderDetailModel {
    private UserOrderModel userOrderModel;
    private AddressModel AddressModel;

    public UserOrderModel getUserOrderModel() {
        return userOrderModel;
    }
    public void setUserOrderModel(UserOrderModel userOrderModel) {
        this.userOrderModel = userOrderModel;
    }
    public AddressModel getAddressModel() {
        return AddressModel;
    }
    public void setAddressModel(AddressModel AddressModel) {
        this.AddressModel = AddressModel;
    }

    @Override
    public String toString() {
        return "OpsOrderDetailModel [userOrderModel=" + userOrderModel + ", AddressModel="
                + AddressModel + "]";
    }

}
