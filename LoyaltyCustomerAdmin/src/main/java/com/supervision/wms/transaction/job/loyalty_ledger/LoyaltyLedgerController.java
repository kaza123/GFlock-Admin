/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.job.loyalty_ledger;

import com.supervision.wms.transaction.job.loyalty_ledger.model.TLoyaltyLedger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/transaction/ledger")
public class LoyaltyLedgerController {

    @Autowired
    public LoyaltyLedgerService loyaltyLedgerService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public TLoyaltyLedger save(@RequestBody TLoyaltyLedger ledger) {
        return loyaltyLedgerService.save(ledger);
    }
}
