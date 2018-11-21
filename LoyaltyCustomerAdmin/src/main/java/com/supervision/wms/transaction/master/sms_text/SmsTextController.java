/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.sms_text;

import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
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
@RequestMapping("/api/sv/master/sms-text")
public class SmsTextController {
    @Autowired
    private SmsTextService smsTextService;
    
    @RequestMapping(value = "/get-sms-text-by-name/{name}" , method = RequestMethod.GET)
    public MSmsText getSmsText(@PathVariable String name){
        return smsTextService.findByName(name); 
    }
}
