/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.loyalty_ledger.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_loyalty_ledger")
public class TLoyaltyLedger implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "loyalty_costomer")
    private int loyaltyCostomer;
    
    @Column(name = "`date`")
    private String date;
   
    @Column(name = "point_type")
    private String pointType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "point_in")
    private BigDecimal pointIn;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "point_out")
    private BigDecimal pointOut;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount_pre")
    private BigDecimal discountPre;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount_value")
    private BigDecimal discountValue;
    
    @Column(name = "sms_discount")
    private Integer smsDiscount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private String type;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "type2")
    private String type2;
    
    @Column(name = "inv_no")
    private String invNo;
    
    @Column(name = "inv_date")
    private String invDate;
    
    @Column(name = "item_qty")
    private Integer itemQty;
    
    @Column(name = "item_value")
    private BigDecimal itemValue;
    
    @Column(name = "inv_branch")
    private Integer invBranch;

    public TLoyaltyLedger() {
    }

    public TLoyaltyLedger(Integer indexNo, int loyaltyCostomer, String date, String pointType, BigDecimal pointIn, BigDecimal pointOut, BigDecimal discountPre, BigDecimal discountValue, Integer smsDiscount, String type, String type2, String invNo, String invDate, Integer itemQty, BigDecimal itemValue, Integer invBranch) {
        this.indexNo = indexNo;
        this.loyaltyCostomer = loyaltyCostomer;
        this.date = date;
        this.pointType = pointType;
        this.pointIn = pointIn;
        this.pointOut = pointOut;
        this.discountPre = discountPre;
        this.discountValue = discountValue;
        this.smsDiscount = smsDiscount;
        this.type = type;
        this.type2 = type2;
        this.invNo = invNo;
        this.invDate = invDate;
        this.itemQty = itemQty;
        this.itemValue = itemValue;
        this.invBranch = invBranch;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public int getLoyaltyCostomer() {
        return loyaltyCostomer;
    }

    public void setLoyaltyCostomer(int loyaltyCostomer) {
        this.loyaltyCostomer = loyaltyCostomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public BigDecimal getPointIn() {
        return pointIn;
    }

    public void setPointIn(BigDecimal pointIn) {
        this.pointIn = pointIn;
    }

    public BigDecimal getPointOut() {
        return pointOut;
    }

    public void setPointOut(BigDecimal pointOut) {
        this.pointOut = pointOut;
    }

    public BigDecimal getDiscountPre() {
        return discountPre;
    }

    public void setDiscountPre(BigDecimal discountPre) {
        this.discountPre = discountPre;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getSmsDiscount() {
        return smsDiscount;
    }

    public void setSmsDiscount(Integer smsDiscount) {
        this.smsDiscount = smsDiscount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getInvBranch() {
        return invBranch;
    }

    public void setInvBranch(Integer invBranch) {
        this.invBranch = invBranch;
    }

   

}
