/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant.model;

import java.io.Serializable;
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
@Table(name = "m_plant")
public class MPlant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo = null;

    @Column(name = "loyalty_customer")
    private Integer loyaltyCustomer;

    @Column(name = "branch")
    private Integer branch;
   
    @Column(name = "date")
    private String date;

    @Column(name = "plant_name")
    private String plantName;
   
    @Column(name = "user")
    private String user;

    public MPlant() {
    }

    public MPlant(Integer loyaltyCustomer, Integer branch, String date, String plantName, String user) {
        this.loyaltyCustomer = loyaltyCustomer;
        this.branch = branch;
        this.date = date;
        this.plantName = plantName;
        this.user = user;
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

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    
   
}
