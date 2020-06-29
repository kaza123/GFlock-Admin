/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class MKPI implements Serializable{
    private String collection;
    private String fromDate;
    private String toDate;
    private String style;
    private String category;
    private String subCategory;
    private String designer;
    private String branch;

    public MKPI() {
    }

    public MKPI(String collection, String fromDate, String toDate, String style, String category, String subCategory, String designer, String branch) {
        this.collection = collection;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.style = style;
        this.category = category;
        this.subCategory = subCategory;
        this.designer = designer;
        this.branch = branch;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
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

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
}
