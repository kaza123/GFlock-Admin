/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.home;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class HomeService {

    @Autowired
    public HomeRepository homeRepository;

    public List<Object[]> chart1(Integer  branch,String date) {
        return homeRepository.chart1(branch,date);
    }

    public List<Object[]> chart2(Integer  branch,String date) {
        return homeRepository.getCategoryDetails(branch,date);
    }

    public List<Object[]> getCategoryDetails(Integer  branch,String date) {
        return homeRepository.getCategoryDetails(branch,date);
    }

    public List<Object[]> getSubCategoryDetails(String category,Integer  branch,String date) {
        return homeRepository.getSubCategoryDetails(category,branch,date);
    }
   
    public List<Object[]> getBefore7DaysSalesIncome(Integer  branch,String date) {
        return homeRepository.getBefore7DaysSalesIncome(branch,date);
    }

    public BigDecimal todaySalesIncome(Integer  branch,String date) {
        return homeRepository.todaySalesIncome(branch,date);
    }

    public BigDecimal uptoDateIncome(Integer  branch,String date) {
        return homeRepository.uptoDateIncome(branch,date);
    }
    public BigDecimal lastMonthSales(Integer  branch,String date) {
        return homeRepository.lastMonthSales(branch,date);
    }

    public HashMap getBasketValues(Integer  branch,String date) {
        HashMap<String, Double> map = new HashMap();
        map.put("today", homeRepository.getTodayBasketValue(branch,date).doubleValue());
        map.put("month", homeRepository.getMonthBasketValue(branch,date).doubleValue());
        return map;
    }

}
