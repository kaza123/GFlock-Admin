/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_calender_update;

import com.supervision.wms.transaction.master.loyalty_calender_update.model.MLoyaltyCalenderUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class CalenderUpdateService {

    @Autowired
    private CalenderUpdateRepository calenderUpdateRepository;

    public MLoyaltyCalenderUpdate save(MLoyaltyCalenderUpdate calenderUpdate) {
        return calenderUpdateRepository.save(calenderUpdate);
    }
}
