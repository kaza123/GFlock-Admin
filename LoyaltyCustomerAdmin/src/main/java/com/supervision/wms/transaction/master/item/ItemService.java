/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.item;

import com.supervision.wms.transaction.master.item.model.MItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class ItemService {

    @Autowired
    public ItemRepository itemRepository;

    public List<MItem> categoryList() {
        return itemRepository.categoryList();
    }

    public List<MItem> subCategoryList() {
        return itemRepository.subCategoryList();
    }

    public List<MItem> styleList() {
        return itemRepository.styleList();
    }
    
    public List<MItem> collectionList() {
        return itemRepository.collectionList();
    }

    public List<MItem> styleListByCollection(String collection) {
        return itemRepository.styleListByCollection(collection);
    }

}
