/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.designer;

import com.supervision.wms.transaction.master.designer.model.MDesigner;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class DesignerService {

    @Autowired
    private DesignerRepository designerRepository;

    public ArrayList<MDesigner> findAllDesigner() {
        ArrayList<MDesigner> retList = new ArrayList<>();
        ArrayList<Object[]> designerList = designerRepository.getAllDesigner();
        for (Object[] object : designerList) {
            MDesigner mDesigner = new MDesigner();
            mDesigner.setDesignerId(Integer.parseInt(object[0].toString()));
            mDesigner.setDesignerName(object[1].toString());
            retList.add(mDesigner);
        }
        return retList;
    }

}
