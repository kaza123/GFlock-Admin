/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.sms_discount;

import com.supervision.wms.transaction.job.sms_discount.model.MSmsDiscount;
import com.supervision.wms.transaction.job.sms_discount.model.SmsDiscount;
import com.supervision.wms.transaction.master.loyalty_customer.LoyaltyCustomerService;
import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import com.supervision.wms.transaction.master.loyalty_type.LoyaltyTypeService;
import com.supervision.wms.transaction.master.sms.SmsService;
import com.supervision.wms.transaction.master.sms_text.SmsTextService;
import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import com.supervision.wms.transaction.security.user.UserService;
import com.supervision.wms.transaction.security.user.model.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
public class SmsDiscountService {
    @Autowired
    public SmsDiscountRepository smsDiscountRepository;
    
    @Autowired
    public SmsService smsService;
    
    @Autowired
    public SmsTextService smsTextService;
    
    @Autowired
    public LoyaltyCustomerService customerService;
    
    @Autowired
    public LoyaltyTypeService loyaltyTypeService;
    
    @Autowired
    public UserService userService;
    
    @Transactional
    public MSmsDiscount save(SmsDiscount smsDiscount) {
        List<User> userList = userService.findByNameAndPassword(smsDiscount.getUserName(), smsDiscount.getPassword());
        if (userList.size()<=0 || userList.isEmpty()) {
            throw new RuntimeException("Incorrect Transaction Password !");
        }
        MSmsDiscount sms = new MSmsDiscount();
        String randomNo = smsService.getRandomNo();
        
        if (smsDiscount.getLoyaltyIndex()==null || smsDiscount.getLoyaltyIndex()<=0) {
//            save new Customer
            MLoyaltyCustomer loyaltyCustomer = new MLoyaltyCustomer();
            loyaltyCustomer.setLoyaltyNo(smsDiscount.getMobileNo());
            loyaltyCustomer.setMobileNo(smsDiscount.getMobileNo());
            loyaltyCustomer.setName(smsDiscount.getName());
            loyaltyCustomer.setIsDelete(false);
            loyaltyCustomer.setIsSms(true);
            loyaltyCustomer.setLoyaltyType(loyaltyTypeService.findByName("CUSTOMER").getIndexNo());
            loyaltyCustomer.setProStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRegDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRecidance(smsDiscount.getRecidance());
            Integer save = customerService.save(loyaltyCustomer);
            if (save>0) {
                smsDiscount.setLoyaltyIndex(save);
            }else{
            throw new RuntimeException("Loyalty Customer save fail !");
            }
        }
        
        sms.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sms.setDiscount(smsDiscount.getDiscount());
        sms.setIsActive(true);
        sms.setLoyaltyCustomer(smsDiscount.getLoyaltyIndex());
        sms.setSmsCode(randomNo);
        MSmsDiscount save = smsDiscountRepository.save(sms);
        if (save.getIndexNo()>0) {
            MSmsText textModel = smsTextService.findByName("SMS_DISCOINT");
            String smsText="Hi "+smsDiscount.getRecidance()+" "+smsDiscount.getName()+" ";
            smsText+=textModel.getText1()+" "+randomNo+" "+textModel.getText2();
            
            Integer sendSms = smsService.sendSms(smsDiscount.getMobileNo(), smsText);
            if (sendSms!=200) {
                throw new RuntimeException("sms send failed!");
            }
        }else{
            throw new RuntimeException("Loyalty Ledger save fail !");
        }
        
        
        return save;
    }

    public List<Object[]> getTop20(Integer count) {
        return smsDiscountRepository.getTop20(count);
    }

    public HashMap getCount() {
        String counts = smsDiscountRepository.getCounts();
        HashMap<Integer, String> map = new HashMap();
        map.put(1, counts);
        return map;
    }

    public List<Object[]> getTop20ByMobile(String mobile, Integer count) {
        return smsDiscountRepository.getTop20ByMobile(mobile,count);
        
    }
    
}
