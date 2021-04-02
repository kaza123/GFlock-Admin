/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.security.user.model;

import java.io.Serializable;

/**
 *
 * @author SV Kasun
 */
public class UpdateUser implements Serializable {

    private Integer userIndex;
    private String userName;
    private String password;
    private String newPassword;
    private String reEnterPassword;

    public UpdateUser() {
    }

    public UpdateUser(Integer userIndex, String userName, String password, String newPassword, String reEnterPassword) {
        this.userIndex = userIndex;
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
        this.reEnterPassword = reEnterPassword;
    }

    public Integer getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(Integer userIndex) {
        this.userIndex = userIndex;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }
    
}
