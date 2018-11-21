/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant;

import com.supervision.wms.transaction.master.loyalty_customer.LoyaltyCustomerService;
import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import com.supervision.wms.transaction.master.loyalty_type.LoyaltyTypeService;
import com.supervision.wms.transaction.master.plant.model.MPlant;
import com.supervision.wms.transaction.master.plant.model.PlantDetail;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class PlantService {

    @Autowired
    PlantRepository plantRepository;
  
    @Autowired
    LoyaltyCustomerService loyaltyCustomerService;

    @Autowired
    public LoyaltyTypeService loyaltyTypeService;
    
    public MPlant save(PlantDetail plantDetail) {
        if (null==plantDetail.getLoyaltyIndex() || plantDetail.getLoyaltyIndex()<=0) {
            //new loyalty customer
            MLoyaltyCustomer loyaltyCustomer = new MLoyaltyCustomer();
            loyaltyCustomer.setLoyaltyNo(plantDetail.getMobileNo());
            loyaltyCustomer.setMobileNo(plantDetail.getMobileNo());
            loyaltyCustomer.setName(plantDetail.getName());
            loyaltyCustomer.setIsDelete(false);
            loyaltyCustomer.setIsSms(true);
            loyaltyCustomer.setLoyaltyType(loyaltyTypeService.findByName("CUSTOMER").getIndexNo());
            loyaltyCustomer.setProStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRegDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRecidance(plantDetail.getRecidance());
            Integer save = loyaltyCustomerService.save(loyaltyCustomer);
            plantDetail.setLoyaltyIndex(save);
        }
        MPlant plant = new MPlant();
        plant.setLoyaltyCustomer(plantDetail.getLoyaltyIndex());
        plant.setBranch(plantDetail.getBranch());
        plant.setPlantName(plantDetail.getPlantName());
        plant.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        plant.setUser(plantDetail.getUser());
        
        return plantRepository.save(plant);
    }

    public List<Object[]> getTop(Integer limit) {
       return plantRepository.getTop(limit);
    }

    public List<Object[]> getTop(Integer limit, String mobile) {
       return plantRepository.getTopByMobile(limit,mobile);
    }

    public HashMap getCount() {
        Integer count= plantRepository.getCount();
        HashMap<Integer, Integer> map = new HashMap();
        map.put(1, count);
        return map;
    }

    public List<Object> getPlants() {
        return plantRepository.getPlants();
    }

}
