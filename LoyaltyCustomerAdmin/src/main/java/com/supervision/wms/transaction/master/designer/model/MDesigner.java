/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.designer.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class MDesigner implements Serializable{
    private Integer designerId;
    private String designerName;

    public MDesigner() {
    }

    public MDesigner(Integer designerId, String designerName) {
        this.designerId = designerId;
        this.designerName = designerName;
    }

    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
    
    
}
