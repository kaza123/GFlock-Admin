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
public class MIPGReport implements Serializable{

    private String fDate;
    private String tDate;

    public MIPGReport() {
    }

    public MIPGReport(String fDate, String tDate) {
        this.fDate = fDate;
        this.tDate = tDate;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

}
