/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.item;

import com.supervision.wms.transaction.master.item.model.MItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/master/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @RequestMapping(value = "/category-list" , method = RequestMethod.GET)
    public List<MItem> categoryList(){
        return itemService.categoryList();
    }
    @RequestMapping(value = "/sub-category-list" , method = RequestMethod.GET)
    public List<MItem> subCategoryList(){
        return itemService.subCategoryList();
    }
    @RequestMapping(value = "/style-list" , method = RequestMethod.GET)
    public List<MItem> styleList(){
        return itemService.styleList();
    }
    @RequestMapping(value = "/style-list-by-collection/{collection}" , method = RequestMethod.GET)
    public List<MItem> styleListByCollection(@PathVariable String collection){
        return itemService.styleListByCollection(collection);
    }
    @RequestMapping(value = "/collection-list" , method = RequestMethod.GET)
    public List<MItem> collectionList(){
        return itemService.collectionList();
    }
}
