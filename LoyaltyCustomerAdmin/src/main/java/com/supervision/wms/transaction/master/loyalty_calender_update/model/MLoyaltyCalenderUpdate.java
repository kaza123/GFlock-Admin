/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_calender_update.model;

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
@Table(name = "m_loyalty_calander_update")
public class MLoyaltyCalenderUpdate implements Serializable{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo = null;

    @Column(name = "loyalty_calander")
    private Integer loyaltyCalander;

    @Column(name = "`date_time`")
    private String dateTime;

    public MLoyaltyCalenderUpdate() {
    }

    public MLoyaltyCalenderUpdate(Integer loyaltyCalander, String dateTime) {
        this.loyaltyCalander = loyaltyCalander;
        this.dateTime = dateTime;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getLoyaltyCalander() {
        return loyaltyCalander;
    }

    public void setLoyaltyCalander(Integer loyaltyCalander) {
        this.loyaltyCalander = loyaltyCalander;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
}
