/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer_update;

import com.supervision.wms.transaction.master.loyalty_customer_update.model.MLoyaltyCustomerUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyCustomerUpdateService {
    @Autowired
    public LoyaltyCustomerUpdateRepository customerUpdateRepository;
    
     public MLoyaltyCustomerUpdate save(MLoyaltyCustomerUpdate customerUpdate) {
        return customerUpdateRepository.save(customerUpdate);
    }
}
