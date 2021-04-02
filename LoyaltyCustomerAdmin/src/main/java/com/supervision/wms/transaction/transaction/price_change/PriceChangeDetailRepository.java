/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change;

import com.supervision.wms.transaction.transaction.price_change.model.PriceChangeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface PriceChangeDetailRepository extends JpaRepository<PriceChangeDetail, Integer> {
    
}
