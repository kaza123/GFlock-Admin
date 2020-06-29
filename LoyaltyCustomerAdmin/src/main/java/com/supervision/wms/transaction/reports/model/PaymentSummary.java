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
public class PaymentSummary {

    private String fromDate;
    private String toDate;
    private String branch;
    private String terminal;

    public PaymentSummary() {
    }

    public PaymentSummary(String fromDate, String toDate, String branch, String terminal) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.branch = branch;
        this.terminal = terminal;
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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
    
    
}
