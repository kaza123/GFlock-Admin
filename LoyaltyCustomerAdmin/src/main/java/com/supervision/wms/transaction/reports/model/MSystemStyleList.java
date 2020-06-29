/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports.model;

import java.math.BigDecimal;

/**
 *
 * @author kasun
 */
public class MSystemStyleList {
    private String style;
    private String image;
    private BigDecimal price;

    public MSystemStyleList() {
    }

    public MSystemStyleList(String style, String image, BigDecimal price) {
        this.style = style;
        this.image = image;
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
