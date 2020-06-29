/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.designer;

import com.supervision.wms.transaction.master.loyalty_type.model.MLoyaltyType;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kasun
 */
public interface DesignerRepository extends JpaRepository<MLoyaltyType, Integer>{

    @Query(value = "SELECT m_collection_detail.designer_id, m_collection_detail.designer_name\n"
            + "FROM m_collection_detail\n"
            + "GROUP BY m_collection_detail.designer_id", nativeQuery = true)
    public ArrayList<Object[]> getAllDesigner();

}
