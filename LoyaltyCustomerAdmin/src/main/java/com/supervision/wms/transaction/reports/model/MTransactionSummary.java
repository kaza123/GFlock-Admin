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
public class MTransactionSummary implements Serializable {

    private String fromDate;
    private String toDate;
    private String branch;
    private String transaction;
    private String category;
    private String style;
    private String traIndex;
    private String traNumber;

    public MTransactionSummary() {
    }

    public String getTraIndex() {
        return traIndex;
    }

    public void setTraIndex(String traIndex) {
        this.traIndex = traIndex;
    }

    public String getTraNumber() {
        return traNumber;
    }

    public void setTraNumber(String traNumber) {
        this.traNumber = traNumber;
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

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
