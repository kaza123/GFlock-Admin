/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.transaction.price_change;

import com.supervision.wms.transaction.transaction.price_change.model.DepartmentModel;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
public interface PriceChangeRepository extends JpaRepository<DepartmentModel, Integer> {

    @Query(value = "SELECT * FROM m_department ORDER BY department_index", nativeQuery = true)
    public Object[] getAllDepartment();

    @Query(value = "select m_category.category_index,m_category.category_name,m_category.category_short_code\n"
            + "FROM m_category", nativeQuery = true)
    public Object[] getAllCategory();

    @Query(value = "SELECT m_item.item_index,m_item.item_description,m_item.category_index \n"
            + "FROM m_item where m_item.category_index=:category", nativeQuery = true)
    public Object[] getAllItem(@Param("category") Integer category);

    @Query(value = "SELECT m_item.item_index, m_item.item_description,SUM(main_t_stock_ledger.in_qty)-SUM(main_t_stock_ledger.out_qty) AS current_stock\n"
            + ",m_item.selling_price,0 as discount_rate,0 as discount,m_item.selling_price AS new_selling_price\n"
            + "FROM m_item\n"
            + "INNER JOIN pos_m_item ON pos_m_item.item_index=m_item.item_index\n"
            + "INNER JOIN main_t_grn_item ON main_t_grn_item.item=pos_m_item.index_no\n"
            + "INNER JOIN main_t_grn ON main_t_grn.index_no=main_t_grn_item.grn\n"
            + "INNER JOIN main_t_stock_ledger ON main_t_stock_ledger.item=pos_m_item.index_no\n"
            + "INNER JOIN m_category ON m_category.category_index=m_item.category_index\n"
            + "WHERE main_t_grn.transaction_date>=:from_date\n"
            + "AND main_t_grn.transaction_date<=:to_date\n"
            + "AND (:category IS NULL OR m_item.category_index=:category)\n"
            + "AND (:department IS NULL OR m_category.department_index=:department)\n"
            + "AND (:item IS NULL OR m_item.item_index=:item)\n"
            + "GROUP BY m_item.item_index, m_item.item_description", nativeQuery = true)
    public ArrayList<Object[]> getItemDetail(
            @Param("from_date") String from_date,
            @Param("to_date") String to_date,
            @Param("department") Integer department,
            @Param("category") Integer category,
            @Param("item") Integer item);

    @Transactional
    @Modifying
    @Query(value = "UPDATE m_item SET selling_price=:new_selling_price WHERE item_index=:item_index", nativeQuery = true)
    public int changePrice(@Param("item_index") Integer item_index,
            @Param("new_selling_price") double new_selling_price);

    @Transactional
    @Modifying
    @Query(value = "UPDATE pos_m_item SET price=:new_selling_price WHERE item_index=:item_index", nativeQuery = true)
    public int changePosPrice(@Param("item_index") Integer item_index, 
            @Param("new_selling_price") double new_selling_price);

}
