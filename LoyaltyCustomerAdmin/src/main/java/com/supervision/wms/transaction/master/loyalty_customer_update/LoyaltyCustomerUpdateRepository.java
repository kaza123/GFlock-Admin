/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer_update;

import com.supervision.wms.transaction.master.loyalty_customer_update.model.MLoyaltyCustomerUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface LoyaltyCustomerUpdateRepository extends JpaRepository<MLoyaltyCustomerUpdate, Integer>{
    
}
