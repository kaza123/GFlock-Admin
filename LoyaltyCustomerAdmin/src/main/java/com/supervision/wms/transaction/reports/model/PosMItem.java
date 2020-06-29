/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "pos_m_item")
public class PosMItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;
    
    @Size(max = 200)
    @Column(name = "barcode")
    private String barcode;
    
    @Size(max = 200)
    @Column(name = "details")
    private String details;
    
    @Size(max = 200)
    @Column(name = "print_description")
    private String printDescription;
    
    @Column(name = "discount_percentage")
    private Long discountPercentage;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "stock_qty")
    private Long stockQty;
    
    @Size(max = 200)
    @Column(name = "image_code")
    private String imageCode;
    
    @Size(max = 200)
    @Column(name = "category")
    private String category;
    
    @Size(max = 200)
    @Column(name = "sub_category")
    private String subCategory;
    
    @Column(name = "idx")
    private Integer idx;
    
    @Size(max = 50)
    @Column(name = "cost_center")
    private String costCenter;

    public PosMItem() {
    }

    public PosMItem(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrintDescription() {
        return printDescription;
    }

    public void setPrintDescription(String printDescription) {
        this.printDescription = printDescription;
    }

    public Long getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Long discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStockQty() {
        return stockQty;
    }

    public void setStockQty(Long stockQty) {
        this.stockQty = stockQty;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    
}
