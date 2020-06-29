/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.item;

import com.supervision.wms.transaction.master.item.model.MItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface ItemRepository extends JpaRepository<MItem, Integer> {

    @Query(value = "select pos_m_item.index_no,\n"
            + "	pos_m_item.category,\n"
            + "	null as sub_category,\n"
            + "	null as image_code\n"
            + "from pos_m_item\n"
            + "group by pos_m_item.category", nativeQuery = true)
    public List<MItem> categoryList();

    @Query(value = "select pos_m_item.index_no,\n"
            + "	null as category,\n"
            + "	pos_m_item.sub_category as sub_category,\n"
            + "	null as image_code\n"
            + "from pos_m_item\n"
            + "group by pos_m_item.sub_category", nativeQuery = true)
    public List<MItem> subCategoryList();

    @Query(value = "select pos_m_item.index_no, \n"
            + "   pos_m_item.collection_no as category, \n"
            + "   null as sub_category, \n"
            + "   pos_m_item.image_code as image_code \n"
            + "   from pos_m_item \n"
            + "   WHERE pos_m_item.collection_no <> '-'\n"
            + "   group by pos_m_item.image_code,pos_m_item.collection_no\n"
            + " ORDER BY pos_m_item.index_no desc", nativeQuery = true)
    public List<MItem> styleList();

    @Query(value = "select pos_m_item.index_no,\n"
            + "     pos_m_item.collection_no as category,\n"
            + "     null as sub_category,\n"
            + "     null as image_code\n"
            + "from pos_m_item\n"
            + "group by pos_m_item.collection_no\n"
            + "order by pos_m_item.collection_no", nativeQuery = true)
    public List<MItem> collectionList();

    @Query(value = "select pos_m_item.index_no,\n"
            + "     pos_m_item.collection_no as category,\n"
            + "     null as sub_category,\n"
            + "     pos_m_item.image_code as image_code\n"
            + "from pos_m_item\n"
            + "where pos_m_item.collection_no=:collection\n"
            + "group by pos_m_item.image_code\n"
            + "order by pos_m_item.image_code", nativeQuery = true)
    public List<MItem> styleListByCollection(@Param("collection") String collection);

}
