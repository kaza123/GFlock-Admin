/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_calender;

import com.supervision.wms.transaction.master.loyalty_calender.model.MLoyaltyCalender;
import com.supervision.wms.transaction.master.loyalty_calender_update.CalenderUpdateService;
import com.supervision.wms.transaction.master.loyalty_calender_update.model.MLoyaltyCalenderUpdate;
import com.supervision.wms.transaction.master.loyalty_customer_update.model.MLoyaltyCustomerUpdate;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyCalenderService {

    @Autowired
    private LoyaltyCalenderRepository calenderRepository;

    @Autowired
    private CalenderUpdateService calenderUpdateService;

    public MLoyaltyCalender findByDate(String date) {
        return calenderRepository.findByDate(date);

    }

    public List<MLoyaltyCalender> top20(Integer limit) {
        List<MLoyaltyCalender> retList = new ArrayList<>();

        List<Object[]> top20List = calenderRepository.top20(limit);
        for (Object[] object : top20List) {
            MLoyaltyCalender calender = new MLoyaltyCalender();
            calender.setIndexNo(Integer.parseInt(object[0].toString()));
            calender.setDate(object[1].toString());
            calender.setPoint(Integer.parseInt(object[2].toString()));
            calender.setPointValue(new BigDecimal(object[3].toString()));
//            calender.setIsActive(Boolean.valueOf(object[4].toString()));
            //System.out.println(object[4].);

            calender.setIsActive("1".equals(object[4].toString()));
            retList.add(calender);
        }
        return retList;

    }

    public MLoyaltyCalender save(MLoyaltyCalender calender) {
        System.out.println("calender.getIndexNo()   "+calender.getIndexNo());
        if (calender.getIndexNo() == null) {
//            save
            return calenderRepository.save(calender);
        } else {
//            update
            MLoyaltyCalender save = calenderRepository.save(calender);
//            calender update table insert new row
            MLoyaltyCalenderUpdate update = new MLoyaltyCalenderUpdate();
            update.setLoyaltyCalander(save.getIndexNo());
            update.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            calenderUpdateService.save(update);
            return save;

        }
    }

}
