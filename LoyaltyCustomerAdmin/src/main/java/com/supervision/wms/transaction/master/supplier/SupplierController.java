/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.supplier;

import com.supervision.wms.transaction.master.supplier.model.MSupplier;
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
@RequestMapping("/api/sv/master/supplier")
public class SupplierController {
      @Autowired
    private SupplierService supplierService;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<MSupplier> findSupplier(){
        return supplierService.findSupplier();
        
    }
}
