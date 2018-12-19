/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.point_type;

import com.supervision.wms.transaction.master.point_type.model.MPointType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface PointTypeRepository extends JpaRepository<MPointType, Integer> {

    public List<MPointType> findByIsActive(boolean b);

    @Query(value = "select * from m_loyalty_calander\n"
            + "order by m_loyalty_calander.date desc limit :limit", nativeQuery = true)
    public List<Object[]> top20(@Param("limit") Integer limit);

}
