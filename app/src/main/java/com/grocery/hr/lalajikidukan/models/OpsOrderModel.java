package com.grocery.hr.lalajikidukan.models;

import java.util.List;

/**
 * Created by Rohit on 25-04-2017.
 */


public class OpsOrderModel {

    List<OpsOrderDetailModel> opsOrderDetailModel;
    int totalOrderCount;

    public List<OpsOrderDetailModel> getOpsOrderDetailModel() {
        return opsOrderDetailModel;
    }
    public void setOpsOrderDetailModel(List<OpsOrderDetailModel> opsOrderDetailModel) {
        this.opsOrderDetailModel = opsOrderDetailModel;
    }
    public int getTotalOrderCount() {
        return totalOrderCount;
    }
    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    @Override
    public String toString() {
        return "OpsOrderModel [opsOrderDetailModel=" + opsOrderDetailModel + ", totalOrderCount=" + totalOrderCount
                + "]";
    }

}