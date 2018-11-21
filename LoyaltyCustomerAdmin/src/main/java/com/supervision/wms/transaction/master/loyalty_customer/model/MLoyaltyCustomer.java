/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_loyalty_customer")
public class MLoyaltyCustomer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo = null;

    @Basic(optional = false)
    @NotNull
    @Column(name = "recidance")
    private String recidance;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "mobile_no")
    private String mobileNo = null;

    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "loyalty_no")
    private String loyaltyNo = null;

    @Size(max = 20)
    @Column(name = "nic")
    private String nic;

    @Size(max = 500)
    @Column(name = "address")
    private String address;

    @Size(max = 50)
    @Column(name = "city")
    private String city;

    @Column(name = "b_year")
    private Integer bYear;

    @Column(name = "b_month")
    private Integer bMonth;

    @Column(name = "b_date")
    private Integer bDate;

    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @Column(name = "loyalty_type")
    private int loyaltyType;

    @Basic(optional = false)
    @Column(name = "reg_date")
    private String regDate;

    @Basic(optional = false)
    @Column(name = "pro_start_date")
    private String proStartDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_sms")
    private boolean isSms;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_delete")
    private boolean isDelete;

    public MLoyaltyCustomer() {
    }

    public MLoyaltyCustomer(Integer indexNo, String recidance, String name, String mobileNo, String loyaltyNo, String nic, String address, String city, Integer bYear, Integer bMonth, Integer bDate, String email, int loyaltyType, String regDate, String proStartDate, boolean isSms, boolean isDelete) {
        this.indexNo = indexNo;
        this.recidance = recidance;
        this.name = name;
        this.mobileNo = mobileNo;
        this.loyaltyNo = loyaltyNo;
        this.nic = nic;
        this.address = address;
        this.city = city;
        this.bYear = bYear;
        this.bMonth = bMonth;
        this.bDate = bDate;
        this.email = email;
        this.loyaltyType = loyaltyType;
        this.regDate = regDate;
        this.proStartDate = proStartDate;
        this.isSms = isSms;
        this.isDelete = isDelete;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getRecidance() {
        return recidance;
    }

    public void setRecidance(String recidance) {
        this.recidance = recidance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLoyaltyNo() {
        return loyaltyNo;
    }

    public void setLoyaltyNo(String loyaltyNo) {
        this.loyaltyNo = loyaltyNo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getbYear() {
        return bYear;
    }

    public void setbYear(Integer bYear) {
        this.bYear = bYear;
    }

    public Integer getbMonth() {
        return bMonth;
    }

    public void setbMonth(Integer bMonth) {
        this.bMonth = bMonth;
    }

    public Integer getbDate() {
        return bDate;
    }

    public void setbDate(Integer bDate) {
        this.bDate = bDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoyaltyType() {
        return loyaltyType;
    }

    public void setLoyaltyType(int loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getProStartDate() {
        return proStartDate;
    }

    public void setProStartDate(String proStartDate) {
        this.proStartDate = proStartDate;
    }

    public boolean isIsSms() {
        return isSms;
    }

    public void setIsSms(boolean isSms) {
        this.isSms = isSms;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isValid() {
        return indexNo != null && mobileNo != null && loyaltyNo != null;
    }
}
