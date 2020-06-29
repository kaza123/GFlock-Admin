/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.branch;

import com.supervision.wms.transaction.master.branch.model.MainMBranch;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class BranchService {

    @Autowired
    public BranchRepository branchRepository;

    public List<MainMBranch> findByType2() {
        return branchRepository.findByType2("SALES_BRANCH");
    }

    public List<MainMBranch> findByType2OrType2() {
        return branchRepository.findByType2OrType2("SALES_BRANCH","MAIL_BRANCH");
    }

    public List<MainMBranch> findByIsDashboard() {
        return branchRepository.findByIsDashboard(1);
    }

}
