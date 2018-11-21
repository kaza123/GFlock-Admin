/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.sms_discount.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author kasun
 */
public class SmsDiscount implements Serializable{
    private Integer loyaltyIndex;
    private String mobileNo;
    private String recidance;
    private String name;
    private BigDecimal discount;
    private String userName;
    private String password;

    public SmsDiscount() {
    }

    public SmsDiscount(Integer loyaltyIndex, String mobileNo, String recidance, String name, BigDecimal discount, String userName, String password) {
        this.loyaltyIndex = loyaltyIndex;
        this.mobileNo = mobileNo;
        this.recidance = recidance;
        this.name = name;
        this.discount = discount;
        this.userName = userName;
        this.password = password;
    }

    public Integer getLoyaltyIndex() {
        return loyaltyIndex;
    }

    public void setLoyaltyIndex(Integer loyaltyIndex) {
        this.loyaltyIndex = loyaltyIndex;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRecidance() {
        return recidance;
    }

    public void setRecidance(String recidance) {
        this.recidance = recidance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
}
