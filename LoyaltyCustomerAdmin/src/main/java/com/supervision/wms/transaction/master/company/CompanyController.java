/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.company;

import com.supervision.wms.transaction.master.branch.BranchService;
import com.supervision.wms.transaction.master.branch.model.MainMBranch;
import com.supervision.wms.transaction.master.company.model.MCompany;
import java.util.List;
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
@RequestMapping("/api/sv/master/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    
      @RequestMapping(value = "/find-company" , method = RequestMethod.GET)
    public List<MCompany> findCompany(){
        return companyService.findCompany();
        
    }
}
