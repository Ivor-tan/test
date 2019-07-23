package com.example.mytest;

import java.util.List;



public class PayPropertyFeeInfoBean {

    private int id;
    private int moneys;
    private String fee_year;
    private String fee_month;
    private Boolean checkBox_state = false;//存储checkBox点击改变状态

    public Boolean getCheckBox_state() {
        return checkBox_state;
    }

    public void setCheckBox_state(Boolean checkBox_state) {
        this.checkBox_state = checkBox_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoneys() {
        return moneys;
    }

    public void setMoneys(int moneys) {
        this.moneys = moneys;
    }

    public String getFee_year() {
        return fee_year;
    }

    public void setFee_year(String fee_year) {
        this.fee_year = fee_year;
    }

    public String getFee_month() {
        return fee_month;
    }

    public void setFee_month(String fee_month) {
        this.fee_month = fee_month;
    }
}
