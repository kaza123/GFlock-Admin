/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.sms_text;

import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class SmsTextService {
    @Autowired
    public SmsTextRepository smsTextRepository;
    
    public MSmsText findByName(String name) {
        return smsTextRepository.findByName(name);
    }
    
}
