/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_calender;

import com.supervision.wms.transaction.master.loyalty_calender.model.MLoyaltyCalender;
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
@RequestMapping("/api/sv/master/calender")
public class LoyaltyCalenderController {
    @Autowired
    private LoyaltyCalenderService calenderService;
    
    @RequestMapping(value = "/find-by-date/{date}" , method = RequestMethod.GET)
    public MLoyaltyCalender fingByDate(@PathVariable String date){
        return calenderService.findByDate(date);
    }
    @RequestMapping(value = "/top-20/{limit}", method = RequestMethod.GET)
    public List<MLoyaltyCalender> top20(@PathVariable Integer limit) {
        return calenderService.top20(limit);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MLoyaltyCalender save(@RequestBody MLoyaltyCalender calender) {
        System.out.println(calender.getDate());
        System.out.println(calender.getPointValue());
        System.out.println(calender.getPoint());
        System.out.println(calender.getIsActive());
        System.out.println("((((((((((((((((((((((");
        return calenderService.save(calender);
    }

}

