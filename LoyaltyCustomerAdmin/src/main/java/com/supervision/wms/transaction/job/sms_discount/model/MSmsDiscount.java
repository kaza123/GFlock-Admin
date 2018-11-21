/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.sms_discount.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_sms_discount")
public class MSmsDiscount implements Serializable{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "loyalty_customer")
    private Integer loyaltyCustomer;
    
    @Column(name = "discount")
    private BigDecimal discount;
    
    @Column(name = "sms_code")
    private String smsCode;
    
    @Column(name = "created_date")
    private String createdDate;
    
    @Column(name = "inv_no")
    private String invNo;
    
    @Column(name = "check_date")
    private String checkDate;
    
    @Column(name = "check_branch")
    private String checkBranch;
    
    @Column(name = "is_active")
    private Boolean isActive;

    public MSmsDiscount() {
    }

    public MSmsDiscount(Integer indexNo, Integer loyaltyCustomer, BigDecimal discount, String smsCode, String createdDate, String invNo, String checkDate, String checkBranch, Boolean isActive) {
        this.indexNo = indexNo;
        this.loyaltyCustomer = loyaltyCustomer;
        this.discount = discount;
        this.smsCode = smsCode;
        this.createdDate = createdDate;
        this.invNo = invNo;
        this.checkDate = checkDate;
        this.checkBranch = checkBranch;
        this.isActive = isActive;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getLoyaltyCustomer() {
        return loyaltyCustomer;
    }

    public void setLoyaltyCustomer(Integer loyaltyCustomer) {
        this.loyaltyCustomer = loyaltyCustomer;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckBranch() {
        return checkBranch;
    }

    public void setCheckBranch(String checkBranch) {
        this.checkBranch = checkBranch;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    
}
