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
@RequestMapping("/api/sv/home")
public class HomeController {
    @Autowired
    public HomeService homeService;

    @RequestMapping(value = "/chart1/{branch}/{date}", method = RequestMethod.GET)
    public List<Object[]> chart1(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.chart1(branch,date);
    }
    @RequestMapping(value = "/chart2/{branch}/{date}", method = RequestMethod.GET)
    public List<Object[]> chart2(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.chart2(branch,date);
    }
    
    @RequestMapping(value = "/get-category-details/{branch}/{date}", method = RequestMethod.GET)
    public List<Object[]> getCategoryDetails(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.getCategoryDetails(branch,date);
    }
    
    @RequestMapping(value = "/get-sub-category-details/{category}/{branch}/{date}", method = RequestMethod.GET)
    public List<Object[]> getSubCategoryDetails(@PathVariable String category,@PathVariable Integer branch,@PathVariable String date) {
        return homeService.getSubCategoryDetails(category,branch,date);
    }
    
    @RequestMapping(value = "/today-sales/{branch}/{date}", method = RequestMethod.GET)
    public BigDecimal today(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.todaySalesIncome(branch,date);
    }
    
    @RequestMapping(value = "/upto-date-sales/{branch}/{date}", method = RequestMethod.GET)
    public BigDecimal uptoDateSales(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.uptoDateIncome(branch,date);
    }
    @RequestMapping(value = "/last-month-sales/{branch}/{date}", method = RequestMethod.GET)
    public BigDecimal lastMonthSales(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.lastMonthSales(branch,date);
    }
    
    @RequestMapping(value = "/basket-values/{branch}/{date}", method = RequestMethod.GET)
    public HashMap getBasketValues(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.getBasketValues(branch,date);
    }
    @RequestMapping(value = "/get-sales-before-7-day/{branch}/{date}", method = RequestMethod.GET)
    public List<Object[]> getBefore7DaysSalesIncome(@PathVariable Integer branch,@PathVariable String date) {
        return homeService.getBefore7DaysSalesIncome(branch,date);
    }
}
