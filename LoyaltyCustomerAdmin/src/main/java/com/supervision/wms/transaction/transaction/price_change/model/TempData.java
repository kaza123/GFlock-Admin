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
public class TempData {
//                    "from_date":new Date,
//                    "to_date":new Date,
//                    "department":null,
//                    "category":null,
//                    "item":null

    private String from_date;
    private String to_date;
    private Integer department;
    private Integer category;
    private Integer item;

    public TempData() {
    }

    public TempData(String from_date, String to_date, Integer department, Integer category, Integer item) {
        this.from_date = from_date;
        this.to_date = to_date;
        this.department = department;
        this.category = category;
        this.item = item;
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

}
