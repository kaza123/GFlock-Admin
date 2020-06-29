/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

/**
 *
 * @author kasun
 */
public class MItemWiseGrn {
    public String fromDate;
    public String toDate;
    public String branch;
    public String barcode;
    public String style;
    public String category;
    public String subCategory;
    public String transactionNo;
    public String supplier;

    public MItemWiseGrn() {
    }

    public MItemWiseGrn(String fromDate, String toDate, String branch, String barcode, String style, String category, String subCategory, String transactionNo, String supplier) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.branch = branch;
        this.barcode = barcode;
        this.style = style;
        this.category = category;
        this.subCategory = subCategory;
        this.transactionNo = transactionNo;
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }


    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

   

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
    
}
