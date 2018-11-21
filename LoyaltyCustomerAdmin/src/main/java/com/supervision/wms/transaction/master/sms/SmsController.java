/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.sms;

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
@RequestMapping("/api/sv/master/sms")
public class SmsController {
    @Autowired
    public SmsService smsService;
    
    @RequestMapping(value = "/get-sms-api/{name}" , method = RequestMethod.GET)
    public String getSmsApi(@PathVariable String name){
        return smsService.findByName(name); 
    }
    @RequestMapping(value = "/resent-otp/{mobile}/{otp}" , method = RequestMethod.GET)
    public String getSmsApi(@PathVariable String mobile,@PathVariable String otp){
        return smsService.resentOtp(mobile,otp); 
    }
}
