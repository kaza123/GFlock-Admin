/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change;

import com.supervision.wms.transaction.master.sms_text.SmsTextService;
import com.supervision.wms.transaction.transaction.price_change.model.RequestList;
import com.supervision.wms.transaction.transaction.price_change.model.RowData;
import com.supervision.wms.transaction.transaction.price_change.model.TempData;
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
@RequestMapping("/api/sv/transaction/price_change")
public class PriceChangeController {
    @Autowired
    private PriceChangeService priceChangeService;
    
    @RequestMapping(value = "/get-department" , method = RequestMethod.GET)
    public Object[] getDepartment(){
        return priceChangeService.getAllDepartment(); 
    }
    @RequestMapping(value = "/get-category" , method = RequestMethod.GET)
    public Object[] getCategory(){
        return priceChangeService.getAllCategory(); 
    }
    
    @RequestMapping(value = "/get-item/{category}" , method = RequestMethod.GET)
    public Object[] getAllItem(@PathVariable Integer category){
        return priceChangeService.getAllItem(category); 
    }
    @RequestMapping(value = "/get-item-detail" , method = RequestMethod.POST)
    public List<RowData> getAllItem(@RequestBody TempData temp){
        return priceChangeService.getItemDetail(temp); 
    }
    @RequestMapping(value = "/save" , method = RequestMethod.POST)
    public double save(@RequestBody RequestList requestList){
        return priceChangeService.save(requestList); 
    }
}
