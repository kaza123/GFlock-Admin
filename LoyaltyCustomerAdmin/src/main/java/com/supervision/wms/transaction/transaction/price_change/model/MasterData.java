/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change.model;

/**
 *
 * @author kasun
 */
public class MasterData {
    private int discount_type;
    private double discount;

    public MasterData() {
    }

    public MasterData(int discount_type, double discount) {
        this.discount_type = discount_type;
        this.discount = discount;
    }

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
}
