/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_type;

import com.supervision.wms.transaction.master.loyalty_type.model.MLoyaltyType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/master/loyalty-type")
public class LoyaltyTypeController {
     @Autowired
    public LoyaltyTypeService loyaltyTypeService;
    
    @RequestMapping(value = "/findAll" , method = RequestMethod.GET)
    public List<MLoyaltyType> findAll(){
        return loyaltyTypeService.findAll();
    }
}
