/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.loyalty_ledger;

import com.supervision.wms.transaction.job.loyalty_ledger.model.TLoyaltyLedger;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface LoyaltyLedgerRepository extends JpaRepository<TLoyaltyLedger, Integer> {

    @Query(value = "select sum(t_loyalty_ledger.item_qty) as item_qty,\n"
            + "	sum(t_loyalty_ledger.item_value) as item_value\n"
            + "from 	t_loyalty_ledger\n"
            + "left JOIN m_loyalty_customer on m_loyalty_customer.index_no=t_loyalty_ledger.loyalty_costomer\n"
            + "where m_loyalty_customer.loyalty_no=:loyaltyNo \n"
            + "and t_loyalty_ledger.date BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') and DATE_FORMAT(LAST_DAY(now()),'%Y-%m-%d')", nativeQuery = true)
    public ArrayList<Object[]> getItemCountAndValueForThisMonth(@Param("loyaltyNo")String loyaltyNo);

}
