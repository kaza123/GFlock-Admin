/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.branch;

import com.supervision.wms.transaction.master.branch.model.MainMBranch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface BranchRepository extends JpaRepository<MainMBranch, Integer>{

    public List<MainMBranch> findByType2(String string);
    
}
