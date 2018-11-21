/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_type;

import com.supervision.wms.transaction.master.loyalty_type.model.MLoyaltyType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface LoyaltyTypeRepository extends JpaRepository<MLoyaltyType, Integer>{

    public MLoyaltyType findByName(String name);
    
}
