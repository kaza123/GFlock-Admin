/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.company;

import com.supervision.wms.transaction.master.company.model.MCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface CompanyRepository extends JpaRepository<MCompany, Integer> {
    
}
