/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.sms_discount;

import com.supervision.wms.transaction.job.sms_discount.model.MSmsDiscount;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface SmsDiscountRepository extends JpaRepository<MSmsDiscount, Integer> {

    @Query(value = "select m_loyalty_customer.recidance,\n"
            + "	m_loyalty_customer.name,\n"
            + "	m_loyalty_customer.mobile_no,\n"
            + "	m_sms_discount.discount,\n"
            + "	DATE_FORMAT(m_sms_discount.created_date,'%Y-%m-%d %T'),\n"
            + "	m_sms_discount.is_active,\n"
            + "	m_sms_discount.index_no\n"
            + "from m_sms_discount\n"
            + "left join m_loyalty_customer on m_loyalty_customer.index_no=m_sms_discount.loyalty_customer\n"
            + "order by m_sms_discount.index_no desc limit :limit \n", nativeQuery = true)
    public List<Object[]> getTop20(@Param("limit") Integer limit);

    @Query(value = "select concat((select count(m_sms_discount.index_no) from m_sms_discount where m_sms_discount.is_active=0)+'',\n"
            + "	'/',\n"
            + "	count(m_sms_discount.index_no)+''\n"
            + "	)as count1\n"
            + "from m_sms_discount", nativeQuery = true)
    public String getCounts();

    @Query(value = "select m_loyalty_customer.recidance,\n"
            + "	m_loyalty_customer.name,\n"
            + "	m_loyalty_customer.mobile_no,\n"
            + "	m_sms_discount.discount,\n"
            + "	DATE_FORMAT(m_sms_discount.created_date,'%Y-%m-%d %T'),\n"
            + "	m_sms_discount.is_active,\n"
            + "	m_sms_discount.index_no\n"
            + "from m_sms_discount\n"
            + "left join m_loyalty_customer on m_loyalty_customer.index_no=m_sms_discount.loyalty_customer\n"
            + "where m_loyalty_customer.mobile_no=:mobile\n"
            + "order by m_sms_discount.index_no desc limit :limit", nativeQuery = true)
    public List<Object[]> getTop20ByMobile(@Param("mobile") String mobile, @Param("limit") Integer limit);

}
