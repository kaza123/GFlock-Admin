/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.loyalty_customer;

import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface LoyaltyCustomerRepository extends JpaRepository<MLoyaltyCustomer, Integer> {

    public MLoyaltyCustomer findByMobileNo(String mobile);

    @Query(value = "select m_loyalty_customer.city\n"
            + "from m_loyalty_customer\n"
            + "where m_loyalty_customer.city is not null\n"
            + "group by m_loyalty_customer.city", nativeQuery = true)
    public List<Object> findCityList();

    @Query(value = "select m_loyalty_customer.`*`\n"
            + "   from m_loyalty_customer\n"
            + "   order by index_no desc\n"
            + "   limit :limit", nativeQuery = true)
    public List<MLoyaltyCustomer> findTop20(@Param("limit") Integer limit);

    @Query(value = "select count(m_loyalty_customer.index_no) as count1\n"
            + " from m_loyalty_customer", nativeQuery = true)
    public Integer getCount();

}
