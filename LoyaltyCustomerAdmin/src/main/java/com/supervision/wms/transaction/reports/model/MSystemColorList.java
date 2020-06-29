/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

import java.math.BigDecimal;

/**
 *
 * @author kasun
 */
public class MSystemColorList {

    private String style;
    private String color;
    private BigDecimal cutQty;

    public MSystemColorList() {
    }

    public MSystemColorList(String style, String color, BigDecimal cutQty) {
        this.style = style;
        this.color = color;
        this.cutQty = cutQty;
    }

    public BigDecimal getCutQty() {
        return cutQty;
    }

    public void setCutQty(BigDecimal cutQty) {
        this.cutQty = cutQty;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
