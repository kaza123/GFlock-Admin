/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer;

import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import com.supervision.wms.transaction.master.loyalty_setting.LoyaltySettingService;
import com.supervision.wms.transaction.master.loyalty_setting.model.MLoyaltySetting;
import com.supervision.wms.transaction.master.loyalty_type.LoyaltyTypeService;
import com.supervision.wms.transaction.master.sms.SmsService;
import com.supervision.wms.transaction.master.sms_text.SmsTextService;
import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import com.supervision.wms.transaction.job.loyalty_ledger.LoyaltyLedgerService;
import com.supervision.wms.transaction.job.loyalty_ledger.model.TLoyaltyLedger;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyCustomerService {

    @Autowired
    public LoyaltyCustomerRepository loyaltyCustomerRepository;

    @Autowired
    public SmsService smsService;
//
    @Autowired
    public LoyaltySettingService loyaltySettingService;
//
    @Autowired
    public LoyaltyTypeService loyaltyTypeService;

    @Autowired
    public LoyaltyLedgerService loyaltyLedgerService;

    public MLoyaltyCustomer findByMobileNo(String mobile) {
        return loyaltyCustomerRepository.findByMobileNo(mobile);
    }

    public Integer save(MLoyaltyCustomer loyaltyCustomer) {
        loyaltyCustomer.setName(loyaltyCustomer.getName().toUpperCase());
        return loyaltyCustomerRepository.save(loyaltyCustomer).getIndexNo();
        
    }

    public List<Object> findCityList() {
        return loyaltyCustomerRepository.findCityList();
    }

}
