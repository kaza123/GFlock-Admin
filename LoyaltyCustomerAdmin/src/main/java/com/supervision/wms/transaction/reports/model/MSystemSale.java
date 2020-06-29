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
public class MSystemSale {

    public String fromDate;
    public String toDate;
    public String collection;
    public String category;
    public String styleLabel;

    public MSystemSale() {
    }

    public MSystemSale(String fromDate, String toDate, String collection, String category, String styleLabel) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.collection = collection;
        this.category = category;
        this.styleLabel = styleLabel;
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

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyleLabel() {
        return styleLabel;
    }

    public void setStyleLabel(String styleLabel) {
        this.styleLabel = styleLabel;
    }

}
