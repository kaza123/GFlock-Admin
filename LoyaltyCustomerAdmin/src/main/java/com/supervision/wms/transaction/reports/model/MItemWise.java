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
public class MItemWise {
    public String fromDate;
    public String toDate;
    public String fromTime;
    public String toTime;
    public Integer branch;
    public Integer terminal;
    public Boolean isType;
    public String typeGift;
    public String typeItem;
    public String barcode;
    public String style;
    public String category;
    public String subCategory;

    public MItemWise() {
    }

    public MItemWise(String fromDate, String toDate, String fromTime, String toTime, Integer branch, Integer terminal, Boolean isType, String typeGift, String typeItem, String barcode, String style, String category, String subCategory) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.branch = branch;
        this.terminal = terminal;
        this.isType = isType;
        this.typeGift = typeGift;
        this.typeItem = typeItem;
        this.barcode = barcode;
        this.style = style;
        this.category = category;
        this.subCategory = subCategory;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Boolean getIsType() {
        return isType;
    }

    public void setIsType(Boolean isType) {
        this.isType = isType;
    }

    public String getTypeGift() {
        return typeGift;
    }

    public void setTypeGift(String typeGift) {
        this.typeGift = typeGift;
    }

    public String getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(String typeItem) {
        this.typeItem = typeItem;
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
