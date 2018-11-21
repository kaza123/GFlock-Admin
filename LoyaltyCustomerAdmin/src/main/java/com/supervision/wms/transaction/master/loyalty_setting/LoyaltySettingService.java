/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_setting;

import com.supervision.wms.transaction.master.loyalty_setting.model.MLoyaltySetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltySettingService {
    @Autowired
    public LoyaltySettingRepository loyaltySettingRepository;
    
    public MLoyaltySetting save(MLoyaltySetting mLoyaltySetting) {
        return loyaltySettingRepository.save(mLoyaltySetting);
    }

    public MLoyaltySetting findByName(String name) {
        return loyaltySettingRepository.findByName(name);
    }
    
}
