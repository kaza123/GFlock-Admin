/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change.model;

import java.util.List;

/**
 *
 * @author kasun
 */
public class RequestList {
    private TempData tempData;
    private MasterData masterData;
    private List<RowData> dataList;

    public List<RowData> getDataList() {
        return dataList;
    }

    public void setDataList(List<RowData> dataList) {
        this.dataList = dataList;
    }
    
    public RequestList() {
    }

    public TempData getTempData() {
        return tempData;
    }

    public void setTempData(TempData tempData) {
        this.tempData = tempData;
    }

    public MasterData getMasterData() {
        return masterData;
    }

    public void setMasterData(MasterData masterData) {
        this.masterData = masterData;
    }
    
}
