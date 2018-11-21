/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.security.user.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class User2 implements Serializable{
    private Integer indexNo;
    private Integer branch;
    private String name;
    private String userName;
    private String password;
    private String type;

    public User2() {
    }

    public User2(Integer indexNo, Integer branch, String name, String userName, String password, String type) {
        this.indexNo = indexNo;
        this.branch = branch;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
