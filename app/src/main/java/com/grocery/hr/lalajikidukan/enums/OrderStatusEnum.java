package com.grocery.hr.lalajikidukan.enums;

/**
 * Created by Rohit on 25-04-2017.
 */

public enum OrderStatusEnum {

    PLACED(0), PREPARING(1), DISPATCHED(2), DELIVERED(4), REJECTED(5);


    public int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public static OrderStatusEnum getEnum(int value) {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;// not found
    }

    public int getValue() {
        return value;
    }

    public static OrderStatusEnum getEnumFromString(String status) {
        switch (status) {
            case "PLACED":
                return OrderStatusEnum.PLACED;
            case "PREPARING":
                return OrderStatusEnum.PREPARING;
            case "DISPATCHED":
                return OrderStatusEnum.DISPATCHED;
            case "DELIVERED":
                return OrderStatusEnum.DELIVERED;
            case "REJECTED":
                return OrderStatusEnum.REJECTED;
        }
        return null;
    }

}