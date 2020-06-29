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
public class TGrnSummary implements Serializable {

    private String fromDate;
    private String toDate;
    private String branch;
    private String transactionNo;
    private String referenceNo;
    private String supplier;
    private String style;

    public TGrnSummary() {
    }

    public TGrnSummary(String fromDate, String toDate, String branch, String transactionNo, String referenceNo, String supplier, String style) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.branch = branch;
        this.transactionNo = transactionNo;
        this.referenceNo = referenceNo;
        this.supplier = supplier;
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
}
