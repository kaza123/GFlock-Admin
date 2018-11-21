/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class PlantDetail implements Serializable{
    private Integer indexNo;
    private Integer loyaltyIndex;
    private String mobileNo;
    private String recidance;
    private String name;
    private String address;
    private String city;
    private String email;
    private Integer branch;
    private String plantName;
    private String user;

    public PlantDetail() {
    }

    public PlantDetail(Integer indexNo, Integer loyaltyIndex, String mobileNo, String recidance, String name, String address, String city, String email, Integer branch, String plantName, String user) {
        this.indexNo = indexNo;
        this.loyaltyIndex = loyaltyIndex;
        this.mobileNo = mobileNo;
        this.recidance = recidance;
        this.name = name;
        this.address = address;
        this.city = city;
        this.email = email;
        this.branch = branch;
        this.plantName = plantName;
        this.user = user;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getLoyaltyIndex() {
        return loyaltyIndex;
    }

    public void setLoyaltyIndex(Integer loyaltyIndex) {
        this.loyaltyIndex = loyaltyIndex;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
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
