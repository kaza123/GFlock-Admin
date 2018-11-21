/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_setting;

import com.supervision.wms.transaction.master.loyalty_setting.model.MLoyaltySetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface LoyaltySettingRepository extends JpaRepository<MLoyaltySetting, Integer>{

    public MLoyaltySetting findByName(String name);
    
}
