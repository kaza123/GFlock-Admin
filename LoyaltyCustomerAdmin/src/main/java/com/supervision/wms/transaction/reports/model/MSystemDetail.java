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
public class MSystemDetail {
    private String style;
    private String image;
    private String color;
    private Integer cutQty;
    private Integer branchIndex;
    private String branchName;
    private Integer grnQty;
    private Integer a;
    private Integer b;
    private Integer c;
    private Integer d;
    private Integer e;
    private Integer f;
    private Integer g;
    private Integer totalSoldQty;
    private Integer previousOutQty;
    private Integer balanceQty;
    private BigDecimal price;

    public MSystemDetail() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTotalSoldQty() {
        return totalSoldQty;
    }

    public void setTotalSoldQty(Integer totalSoldQty) {
        this.totalSoldQty = totalSoldQty;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCutQty() {
        return cutQty;
    }

    public void setCutQty(Integer cutQty) {
        this.cutQty = cutQty;
    }

    public Integer getBranchIndex() {
        return branchIndex;
    }

    public void setBranchIndex(Integer branchIndex) {
        this.branchIndex = branchIndex;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getGrnQty() {
        return grnQty;
    }

    public void setGrnQty(Integer grnQty) {
        this.grnQty = grnQty;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getPreviousOutQty() {
        return previousOutQty;
    }

    public void setPreviousOutQty(Integer previousOutQty) {
        this.previousOutQty = previousOutQty;
    }

    public Integer getBalanceQty() {
        return balanceQty;
    }

    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }

   

   
    
}
