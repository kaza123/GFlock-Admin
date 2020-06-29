/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.supplier;

import com.supervision.wms.transaction.master.supplier.model.MSupplier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class SupplierService {

    @Autowired
    public SupplierRepository sRepository;
    
    public List<MSupplier> findSupplier() {
        return sRepository.findAll();
    }
    
}
