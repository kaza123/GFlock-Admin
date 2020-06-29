/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

import java.util.ArrayList;

/**
 *
 * @author kasun
 */
public class MSystemMix {
    private ArrayList<Object[]> colorList;
    private ArrayList<MSystemDetail> detailList;
    private ArrayList<Object[]> styleList;
    private ArrayList<Object[]> branchList;

    public MSystemMix() {
    }

    public MSystemMix(ArrayList<Object[]> colorList, ArrayList<MSystemDetail> detailList, ArrayList<Object[]> styleList, ArrayList<Object[]> branchList) {
        this.colorList = colorList;
        this.detailList = detailList;
        this.styleList = styleList;
        this.branchList = branchList;
    }

    public ArrayList<Object[]> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<Object[]> colorList) {
        this.colorList = colorList;
    }

    public ArrayList<MSystemDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<MSystemDetail> detailList) {
        this.detailList = detailList;
    }

    public ArrayList<Object[]> getStyleList() {
        return styleList;
    }

    public void setStyleList(ArrayList<Object[]> styleList) {
        this.styleList = styleList;
    }

    public ArrayList<Object[]> getBranchList() {
        return branchList;
    }

    public void setBranchList(ArrayList<Object[]> branchList) {
        this.branchList = branchList;
    }

   
   
}
