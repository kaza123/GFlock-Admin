/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant;

import com.supervision.wms.transaction.master.plant.model.MPlant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface PlantRepository extends JpaRepository<MPlant, Integer> {

    @Query(value = "select m_plant.plant_name,\n"
            + "	DATE_FORMAT(m_plant.date,'%Y-%m-%d %T') as date ,\n"
            + "	m_loyalty_customer.mobile_no,\n"
            + "	m_loyalty_customer.recidance,\n"
            + "	m_loyalty_customer.name,\n"
            + " m_plant.index_no\n"
            + "from m_plant\n"
            + "left join m_loyalty_customer on m_loyalty_customer.index_no=m_plant.loyalty_customer\n"
            + "order by m_plant.index_no desc limit :limit", nativeQuery = true)
    public List<Object[]> getTop(@Param("limit") Integer limit);

    @Query(value = "select \n"
            + "	m_plant.plant_name,\n"
            + "	DATE_FORMAT(m_plant.date,'%Y-%m-%d %T') as date ,\n"
            + "	m_loyalty_customer.mobile_no,\n"
            + "	m_loyalty_customer.recidance,\n"
            + "	m_loyalty_customer.name,\n"
            + "	m_plant.index_no\n"
            + "from m_plant\n"
            + "left join m_loyalty_customer on m_loyalty_customer.index_no=m_plant.loyalty_customer\n"
            + "where m_loyalty_customer.mobile_no=:mobile \n"
            + "order by m_plant.index_no desc limit :limit", nativeQuery = true)
    public List<Object[]> getTopByMobile(@Param("limit") Integer limit, @Param("mobile") String mobile);

    @Query(value = "select count(m_plant.index_no) as count1\n"
            + "from m_plant", nativeQuery = true)
    public Integer getCount();

    @Query(value = "select m_plant.plant_name\n"
            + "from m_plant\n"
            + "group by m_plant.plant_name", nativeQuery = true)
    public List<Object> getPlants();

}
