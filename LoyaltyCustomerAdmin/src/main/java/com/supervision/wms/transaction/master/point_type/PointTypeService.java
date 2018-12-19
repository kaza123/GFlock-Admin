/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.point_type;

import com.supervision.wms.transaction.master.point_type.model.MPointType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class PointTypeService {

    @Autowired
    private PointTypeRepository pointTypeRepository;

    public List<MPointType> findByIsAccount() {
        return pointTypeRepository.findByIsActive(true);
    }

   
}
