/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_calender_update;

import com.supervision.wms.transaction.master.loyalty_calender_update.model.MLoyaltyCalenderUpdate;
import com.supervision.wms.transaction.master.loyalty_customer_update.model.MLoyaltyCustomerUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface CalenderUpdateRepository extends JpaRepository<MLoyaltyCalenderUpdate, Integer>{

//    public MLoyaltyCustomerUpdate save(MLoyaltyCalenderUpdate calenderUpdate);
    
}
