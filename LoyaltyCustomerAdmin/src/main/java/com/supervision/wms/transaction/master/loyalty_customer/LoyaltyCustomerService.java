/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer;

import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import com.supervision.wms.transaction.master.loyalty_customer_update.LoyaltyCustomerUpdateService;
import com.supervision.wms.transaction.master.loyalty_customer_update.model.MLoyaltyCustomerUpdate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyCustomerService {

    @Autowired
    public LoyaltyCustomerRepository loyaltyCustomerRepository;
    @Autowired
    public LoyaltyCustomerUpdateService customerUpdateService;

    public MLoyaltyCustomer findByMobileNo(String mobile) {
        return loyaltyCustomerRepository.findByMobileNo(mobile);
    }

    @Transactional
    public Integer save(MLoyaltyCustomer loyaltyCustomer) {
        loyaltyCustomer.setName(loyaltyCustomer.getName().toUpperCase());
        Integer indexNo = loyaltyCustomerRepository.save(loyaltyCustomer).getIndexNo();
        if (loyaltyCustomer.getIndexNo() != null || loyaltyCustomer.getIndexNo() > 0) {
            MLoyaltyCustomerUpdate update = new MLoyaltyCustomerUpdate();
            update.setLoyaltyCustomer(indexNo);
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            update.setUpdatedDate(format);
            customerUpdateService.save(update);
        }
        return indexNo;
    }

    public List<Object> findCityList() {
        return loyaltyCustomerRepository.findCityList();
    }

    public List<MLoyaltyCustomer> findTop20(Integer limit) {
        return loyaltyCustomerRepository.findTop20(limit);
    }

    public Integer getCount() {
        return loyaltyCustomerRepository.getCount();
    }
}
