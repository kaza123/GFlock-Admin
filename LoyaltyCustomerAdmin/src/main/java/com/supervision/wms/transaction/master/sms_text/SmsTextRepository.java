/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.sms_text;

import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface SmsTextRepository extends JpaRepository<MSmsText, Integer>{

    public MSmsText findByName(String name);
    
}
