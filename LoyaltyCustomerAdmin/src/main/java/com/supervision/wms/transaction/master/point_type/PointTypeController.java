/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.point_type;

import com.supervision.wms.transaction.master.point_type.model.MPointType;
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
@RequestMapping("/api/sv/master/point-type")
public class PointTypeController {
     @Autowired
    public PointTypeService pointTypeService;

    @RequestMapping(value = "/find-by-active", method = RequestMethod.GET)
    public List<MPointType> findByIsAccount() {
        return pointTypeService.findByIsAccount();
    }
    
}
