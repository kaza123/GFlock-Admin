/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_setting;

import com.supervision.wms.transaction.master.loyalty_setting.model.MLoyaltySetting;
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
@RequestMapping("/api/sv/master/loyalty-setting")
public class LoyaltySettingController {
    @Autowired
    private LoyaltySettingService loyaltySettingService;
    
    @RequestMapping(value = "/save" , method = RequestMethod.POST)
    public MLoyaltySetting save(@RequestBody MLoyaltySetting mLoyaltySetting){
        return loyaltySettingService.save(mLoyaltySetting); 
    }
    @RequestMapping(value = "/find-by-name/{name}" , method = RequestMethod.GET)
    public MLoyaltySetting save(@PathVariable String name){
        return loyaltySettingService.findByName(name); 
    }
}
