/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.branch;

import com.supervision.wms.transaction.master.branch.model.MainMBranch;
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
@RequestMapping("/api/sv/master/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;
    
    @RequestMapping(value = "/find-sales-branch" , method = RequestMethod.GET)
    public List<MainMBranch> findByType2(){
        return branchService.findByType2();
        
    }
    @RequestMapping(value = "/is_dashboard" , method = RequestMethod.GET)
    public List<MainMBranch> findByIsDashboard(){
        return branchService.findByIsDashboard();
        
    }
    @RequestMapping(value = "/find-sales-main-branch" , method = RequestMethod.GET)
    public List<MainMBranch> findByType2OrType2(){
        return branchService.findByType2OrType2();
        
    }
}
