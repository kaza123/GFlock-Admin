/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant;

import com.supervision.wms.transaction.master.plant.model.MPlant;
import com.supervision.wms.transaction.master.plant.model.PlantDetail;
import java.util.HashMap;
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
@RequestMapping("/api/sv/master/plant")
public class PlantController {

    @Autowired
    public PlantService plantService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MPlant fingByMobile(@RequestBody PlantDetail plant) {
        return plantService.save(plant);
    }
    @RequestMapping(value = "/get-top/{limit}", method = RequestMethod.GET)
    public List<Object[]> getTop(@PathVariable Integer limit) {
        return plantService.getTop(limit);
    }
    @RequestMapping(value = "/get-top/{limit}/{mobile}", method = RequestMethod.GET)
    public List<Object[]> getTop(@PathVariable Integer limit,@PathVariable String mobile) {
        return plantService.getTop(limit,mobile);
    }
    @RequestMapping(value = "/get-plants", method = RequestMethod.GET)
    public List<Object> getPlant() {
        System.out.println("getPlants name");
        return plantService.getPlants();
    }
    @RequestMapping(value = "/get-count", method = RequestMethod.GET)
    public HashMap getCount() {
        return plantService.getCount();
    }
}
