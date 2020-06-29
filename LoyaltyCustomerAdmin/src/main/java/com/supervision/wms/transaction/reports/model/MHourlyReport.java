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
public class MHourlyReport {
    
    public String fDate;
    public Integer branch;
    public String fTime;
    public String tTime;

    public MHourlyReport() {
    }

    public MHourlyReport(String fDate, Integer branch, String fTime, String tTime) {
        this.fDate = fDate;
        this.branch = branch;
        this.fTime = fTime;
        this.tTime = tTime;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }
    
    
    
}
