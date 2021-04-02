/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "price_change_detail")
public class PriceChangeDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "price_change_summary")
    private Integer price_change_summary;

    @Column(name = "item")
    private Integer item;

    @Column(name = "selling_price")
    private double selling_price;

    @Column(name = "discount_rate")
    private double discount_rate;

    @Column(name = "discount")
    private double discount;

    @Column(name = "new_selling_price")
    private double new_selling_price;

    public PriceChangeDetail() {
    }

    public PriceChangeDetail(Integer indexNo, Integer price_change_summary, Integer item, double selling_price, double discount_rate, double discount, double new_selling_price) {
        this.indexNo = indexNo;
        this.price_change_summary = price_change_summary;
        this.item = item;
        this.selling_price = selling_price;
        this.discount_rate = discount_rate;
        this.discount = discount;
        this.new_selling_price = new_selling_price;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getPrice_change_summary() {
        return price_change_summary;
    }

    public void setPrice_change_summary(Integer price_change_summary) {
        this.price_change_summary = price_change_summary;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
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
