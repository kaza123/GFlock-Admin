/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.supervision.wms.transaction.transaction.price_change.model.MasterData;
import com.supervision.wms.transaction.transaction.price_change.model.PriceChangeDetail;
import com.supervision.wms.transaction.transaction.price_change.model.PriceChangeSummary;
import com.supervision.wms.transaction.transaction.price_change.model.RequestList;
import com.supervision.wms.transaction.transaction.price_change.model.RowData;
import com.supervision.wms.transaction.transaction.price_change.model.TempData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
public class PriceChangeService {

    @Autowired
    public PriceChangeRepository priceChangeRepository;

    @Autowired
    public PriceChangeSummaryRepository priceChangeSummaryRepository;

    @Autowired
    public PriceChangeDetailRepository priceChangeDetailRepository;

    public Object[] getAllDepartment() {
        return priceChangeRepository.getAllDepartment();
    }

    public Object[] getAllCategory() {
        return priceChangeRepository.getAllCategory();
    }

    public Object[] getAllItem(Integer category) {
        return priceChangeRepository.getAllItem(category);
    }

    public List<RowData> getItemDetail(TempData temp) {

        ArrayList<RowData> retList = new ArrayList<RowData>();

        ArrayList<Object[]> list = priceChangeRepository.getItemDetail(
                temp.getFrom_date(),
                temp.getTo_date(),
                temp.getDepartment(),
                temp.getCategory(),
                temp.getItem()
        );
        for (Object[] row : list) {
            RowData rowData = new RowData();
            rowData.setItem_index(Integer.parseInt(row[0].toString()));
            rowData.setItem_description(row[1].toString());
            rowData.setCurrent_stock(Double.parseDouble(row[2].toString()));
            rowData.setSelling_price(Double.parseDouble(row[3].toString()));
            rowData.setDiscount_rate(Double.parseDouble(row[4].toString()));
            rowData.setDiscount(Double.parseDouble(row[5].toString()));
            rowData.setNew_selling_price(Double.parseDouble(row[6].toString()));
            retList.add(rowData);
        }
        return retList;
    }

    @Transactional
    public double save(RequestList requestList) {
        MasterData masterData = requestList.getMasterData();
        TempData tempData = requestList.getTempData();
        List<RowData> rowData = requestList.getDataList();

        //insert priceChageSummaary
        PriceChangeSummary priceChangeSummary = new PriceChangeSummary();
        priceChangeSummary.setCategory(tempData.getCategory());
        priceChangeSummary.setDepartment(tempData.getDepartment());
        priceChangeSummary.setDiscount(masterData.getDiscount());
        priceChangeSummary.setDiscount_type(masterData.getDiscount_type());
        priceChangeSummary.setFrom_date(tempData.getFrom_date());
        priceChangeSummary.setItem(tempData.getItem());
        priceChangeSummary.setTo_date(tempData.getTo_date());
        PriceChangeSummary savePriceChangeSummary = priceChangeSummaryRepository.save(priceChangeSummary);
        int summIndex = savePriceChangeSummary.getIndexNo();
        int count=0;

        for (RowData row : rowData) {
            Integer item_index = row.getItem_index();
            double discount_rate = row.getDiscount_rate();
            double discount = row.getDiscount();
            double new_selling_price = row.getNew_selling_price();
            double selling_price = row.getSelling_price();

            System.out.println(item_index);
            System.out.println(discount_rate);
            System.out.println(discount);
            System.out.println(new_selling_price);
            System.out.println(selling_price);

            if (selling_price != new_selling_price) {
                //update m_item
                System.out.println("changePrice &&&&&&&&&&&& "+item_index+" "+ new_selling_price);
                int updateItemPrice = priceChangeRepository.changePrice(item_index, new_selling_price);
                System.out.println("%%%%%%%%% "+ updateItemPrice);

                //update pos_m_item
                System.out.println("changePosPrice &&&&&&&&&&&& "+item_index+" "+ new_selling_price);
                int updatePosItemPrice = priceChangeRepository.changePosPrice(item_index, new_selling_price);
                System.out.println("************* "+ updatePosItemPrice);

                //insert priceChageDetail
                PriceChangeDetail priceChangeDetail = new PriceChangeDetail();
                priceChangeDetail.setDiscount(discount);
                priceChangeDetail.setDiscount_rate(discount_rate);
                priceChangeDetail.setItem(item_index);
                priceChangeDetail.setNew_selling_price(new_selling_price);
                priceChangeDetail.setPrice_change_summary(summIndex);
                priceChangeDetail.setSelling_price(selling_price);
                PriceChangeDetail savePriceChangeDetail = priceChangeDetailRepository.save(priceChangeDetail);
                if (savePriceChangeDetail.getIndexNo()>0) {
                    count++;
                }
            }
        }

        return count;
    }

}
