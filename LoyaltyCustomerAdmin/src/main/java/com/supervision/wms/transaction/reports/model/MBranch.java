/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

/**
 *
 * @author kasun
 */
public class MBranch {
    private Integer branchIndex;
    private String name;

    public MBranch() {
    }

    public MBranch(Integer branchIndex, String name) {
        this.branchIndex = branchIndex;
        this.name = name;
    }

    public Integer getBranchIndex() {
        return branchIndex;
    }

    public void setBranchIndex(Integer branchIndex) {
        this.branchIndex = branchIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
