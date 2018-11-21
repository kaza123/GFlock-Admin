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

}
