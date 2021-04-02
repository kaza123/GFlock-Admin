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
@Table(name = "price_change_summary")
public class PriceChangeSummary implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "from_date")
    private String from_date;

    @Column(name = "to_date")
    private String to_date;
    
    @Column(name = "department")
    private Integer department;
    
    @Column(name = "category")
    private Integer category;
    
    @Column(name = "item")
    private Integer item;
    
    @Column(name = "discount_type")
    private Integer discount_type;
    
    @Column(name = "discount")
    private double discount;

    public PriceChangeSummary() {
    }

    public PriceChangeSummary(Integer indexNo, String from_date, String to_date, Integer department, Integer category, Integer item, Integer discount_type, double discount) {
        this.indexNo = indexNo;
        this.from_date = from_date;
        this.to_date = to_date;
        this.department = department;
        this.category = category;
        this.item = item;
        this.discount_type = discount_type;
        this.discount = discount;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(Integer discount_type) {
        this.discount_type = discount_type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
}
