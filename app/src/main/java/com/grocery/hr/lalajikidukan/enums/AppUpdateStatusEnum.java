package com.grocery.hr.lalajikidukan.enums;

/**
 * Created by vipul on 1/6/17.
 */

public enum AppUpdateStatusEnum {

    NOT_AVAILAIBLE(0), AVAILAIBLE_NOT_MANDATORY(1), MANDATORY(2);

    public int value;

    AppUpdateStatusEnum(int value) {
        this.value = value;
    }

    public static AppUpdateStatusEnum getEnum(int value) {
        for (AppUpdateStatusEnum e : AppUpdateStatusEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;// not found
    }

    public int getValue() {
        return value;
    }

}
