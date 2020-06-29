/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.branch.model;

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
@Table(name = "main_m_branch")
public class MainMBranch implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo = null;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "find_group")
    private String findGroup;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "color")
    private String color;

    @Column(name = "type2")
    private String type2;
   
    @Column(name = "is_dashboard")
    private Integer isDashboard;

    public MainMBranch() {
    }

    public MainMBranch(String branchCode, String name, String type, String findGroup, String addressLine1, String addressLine2, String addressLine3, String telephoneNumber, String color, String type2, Integer isDashboard) {
        this.branchCode = branchCode;
        this.name = name;
        this.type = type;
        this.findGroup = findGroup;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.telephoneNumber = telephoneNumber;
        this.color = color;
        this.type2 = type2;
        this.isDashboard = isDashboard;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFindGroup() {
        return findGroup;
    }

    public void setFindGroup(String findGroup) {
        this.findGroup = findGroup;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public Integer getIsDashboard() {
        return isDashboard;
    }

    public void setIsDashboard(Integer isDashboard) {
        this.isDashboard = isDashboard;
    }

    
}
