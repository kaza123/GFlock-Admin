/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.loyalty_ledger;

import com.supervision.wms.transaction.job.loyalty_ledger.model.TLoyaltyLedger;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class LoyaltyLedgerService {

    @Autowired
    public LoyaltyLedgerRepository loyaltyLedgerRepository;

    public TLoyaltyLedger save(TLoyaltyLedger ledger) {
        return loyaltyLedgerRepository.save(ledger);
    }

    public ArrayList<Object[]> getItemCountAndValueForThisMonth(String loyaltyNo) {
        return loyaltyLedgerRepository.getItemCountAndValueForThisMonth(loyaltyNo);
        
    }

}
