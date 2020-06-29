/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.company;

import com.supervision.wms.transaction.master.branch.BranchRepository;
import com.supervision.wms.transaction.master.company.model.MCompany;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class CompanyService {

     @Autowired
    public CompanyRepository companyRepository;
     
    public List<MCompany> findCompany() {
         return companyRepository.findAll();
    }
    
}
