/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.sms_discount;

import com.supervision.wms.transaction.job.sms_discount.model.MSmsDiscount;
import com.supervision.wms.transaction.job.sms_discount.model.SmsDiscount;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/transaction/sms-discount")
public class SmsDiscountController {

    @Autowired
    public SmsDiscountService smsDiscountService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MSmsDiscount save(@RequestBody SmsDiscount smsDiscount) {
        return smsDiscountService.save(smsDiscount);
    }
    @RequestMapping(value = "/get-sms-discount-detail/{count}", method = RequestMethod.GET)
    public List<Object[]> getTop20(@PathVariable Integer count) {
        return smsDiscountService.getTop20(count);
    }
    @RequestMapping(value = "/get-sms-discount-detail/{mobile}/{count}", method = RequestMethod.GET)
    public List<Object[]> getTop20ByMobile(@PathVariable String mobile,@PathVariable Integer count) {
        return smsDiscountService.getTop20ByMobile(mobile,count);
    }
    @RequestMapping(value = "/get-count", method = RequestMethod.GET)
    public HashMap getCount() {
        return smsDiscountService.getCount();
    }
}
