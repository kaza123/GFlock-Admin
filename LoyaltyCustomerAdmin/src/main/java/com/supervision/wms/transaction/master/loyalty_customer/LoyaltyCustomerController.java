/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer;

import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/master/loyalty")
public class LoyaltyCustomerController {
    @Autowired
    public LoyaltyCustomerService loyaltyCustomerService;
    
    @RequestMapping(value = "/find-by-mobile/{mobile}" , method = RequestMethod.GET)
    public MLoyaltyCustomer fingByMobile(@PathVariable String mobile){
        return loyaltyCustomerService.findByMobileNo(mobile);
    }
    @RequestMapping(value = "/city-list" , method = RequestMethod.GET)
    public List<Object> findCityList(){
        return loyaltyCustomerService.findCityList();
    }
}
