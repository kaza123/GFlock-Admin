/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_type;

import com.supervision.wms.transaction.master.loyalty_type.model.MLoyaltyType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyTypeService {

    @Autowired
    public LoyaltyTypeRepository loyaltyTypeRepository;

    public MLoyaltyType findByName(String name) {
        return loyaltyTypeRepository.findByName(name);
    }

    public List<MLoyaltyType> findAll() {
        return loyaltyTypeRepository.findAll();
    }
}
