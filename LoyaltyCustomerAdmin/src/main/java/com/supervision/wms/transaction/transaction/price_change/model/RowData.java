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
public class RowData {
    private int item_index;
    private String item_description;
    private double current_stock;
    private double selling_price;
    private double discount_rate;
    private double discount;
    private double new_selling_price;

    public RowData() {
    }

    public RowData(int item_index, String item_description, double current_stock, double selling_price, double discount_rate, double discount, double new_selling_price) {
        this.item_index = item_index;
        this.item_description = item_description;
        this.current_stock = current_stock;
        this.selling_price = selling_price;
        this.discount_rate = discount_rate;
        this.discount = discount;
        this.new_selling_price = new_selling_price;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }

    

    public int getItem_index() {
        return item_index;
    }

    public void setItem_index(int item_index) {
        this.item_index = item_index;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public double getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(double current_stock) {
        this.current_stock = current_stock;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNew_selling_price() {
        return new_selling_price;
    }

    public void setNew_selling_price(double new_selling_price) {
        this.new_selling_price = new_selling_price;
    }
    


}
