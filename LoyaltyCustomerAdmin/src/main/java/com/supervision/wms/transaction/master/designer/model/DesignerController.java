/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.designer;

import com.supervision.wms.transaction.master.designer.model.MDesigner;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/master/designer")
public class DesignerController {

    @Autowired
    private DesignerService designerService;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ArrayList<MDesigner> fingAll() {
        return designerService.findAllDesigner();
    }
}
