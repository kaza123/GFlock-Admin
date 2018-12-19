/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.home;

import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface HomeRepository extends JpaRepository<MLoyaltyCustomer, Integer> {

    @Query(value = "select concat(month(pos_t_transaction_summary.tr_date),'-',day(pos_t_transaction_summary.tr_date)) as date1,\n"
            + "round(ifnull(\n"
            + "sum(pos_t_transaction_details.final_value),0.00)) as avarage_income\n"
            + "from pos_t_transaction_summary\n"
            + "left join pos_t_transaction_details on pos_t_transaction_summary.index_no = pos_t_transaction_details.tr_index_no\n"
            + "where pos_t_transaction_summary.tr_date BETWEEN DATE_SUB(:date, INTERVAL 7 day) and :date \n"
            + "and pos_t_transaction_details.tr_det_type = 'Item'\n"
            + "and (:branch = 0 or pos_t_transaction_summary.branch=:branch)\n"
            + "group by day(pos_t_transaction_summary.tr_date)\n"
            + "order by pos_t_transaction_summary.tr_date", nativeQuery = true)
    public List<Object[]> chart1(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value),0.00) as total_item_value\n"
            + "  from temp_tr_details \n"
            + "  left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "  Where tr_det_type='Item'\n"
            + "  and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "  and tr_date1=DATE_FORMAT(:date,'%Y%m%d')", nativeQuery = true)
    public BigDecimal todaySalesIncome(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value),0.00) as total_item_value,\n"
            + "	  DATE_FORMAT(DATE_SUB(:date, INTERVAL 7 DAY), '%W') as date_name \n"
            + "     from temp_tr_details \n"
            + "     left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "     Where tr_det_type='Item'\n"
            + "  and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "     and tr_date1=DATE_FORMAT(DATE_SUB(:date, INTERVAL 7 DAY),'%Y%m%d')", nativeQuery = true)
    public List<Object[]> getBefore7DaysSalesIncome(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value),0.00) as total_item_value\n"
            + "  from temp_tr_details \n"
            + "  left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "  Where tr_det_type='Item'\n"
            + "  and year(tr_date)=year(:date)\n"
            + "  and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "  and month(tr_date)=month(:date)", nativeQuery = true)
    public BigDecimal uptoDateIncome(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value),0.00) as total_item_value\n"
            + "from temp_tr_details \n"
            + "left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "Where tr_det_type='Item'\n"
            + "and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "and year(tr_date)=year(:date)\n"
            + "and month(tr_date)=month(DATE_SUB(:date, INTERVAL 1 month))", nativeQuery = true)
    public BigDecimal lastMonthSales(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select pos_m_item.category,\n"
            + " ifnull(sum(tr_final_value),0.00) as total_item_value,\n"
            + "sum(temp_tr_details.item_qty) as qty ,\n"
            + "pos_m_item.category as cat2\n"
            + "from temp_tr_details \n"
            + "left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "Where tr_det_type='Item'\n"
            + "and tr_date1=DATE_FORMAT(:date,'%Y%m%d')\n"
            + "and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "group by pos_m_item.category", nativeQuery = true)
    public List<Object[]> getCategoryDetails(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select pos_m_item.sub_category,\n"
            + " ifnull(sum(tr_final_value),0.00) as total_item_value,\n"
            + "sum(temp_tr_details.item_qty) as qty \n"
            + "from temp_tr_details \n"
            + "left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "Where tr_det_type='Item'\n"
            + "and tr_date1=DATE_FORMAT(:date,'%Y%m%d')\n"
            + "and pos_m_item.category=:category\n"
            + "and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "group by pos_m_item.sub_category", nativeQuery = true)
    public List<Object[]> getSubCategoryDetails(@Param("category") String category,@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value)/\n"
            + "(Select count(pos_t_transaction_summary.index_no)\n"
            + "  from pos_t_transaction_summary\n"
            + "  where pos_t_transaction_summary.tr_type='invoice'\n"
            + "  and(:branch = 0 or pos_t_transaction_summary.branch=:branch)\n"
            + "  and pos_t_transaction_summary.tr_date1=DATE_FORMAT(:date,'%Y%m%d')\n"
            + "  ),0.00) \n"
            + "as total_item_value\n"
            + "  from temp_tr_details \n"
            + "  left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "  Where tr_det_type='Item'\n"
            + "  and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "  and tr_date1=DATE_FORMAT(:date,'%Y%m%d')", nativeQuery = true)
    public BigDecimal getTodayBasketValue(@Param("branch") Integer branch,@Param("date") String date);

    @Query(value = "Select ifnull(sum(tr_final_value)/\n"
            + "(Select count(pos_t_transaction_summary.index_no)\n"
            + "  from pos_t_transaction_summary\n"
            + "  where pos_t_transaction_summary.tr_type='invoice'\n"
             + "  and(:branch = 0 or pos_t_transaction_summary.branch=:branch)\n"
            + "  and year(pos_t_transaction_summary.tr_date)=year(:date)\n"
            + "  and month(pos_t_transaction_summary.tr_date)=month(:date)\n"
            + "  ) ,0.00)\n"
            + "as total_item_value\n"
            + "  from temp_tr_details \n"
            + "  left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "  Where tr_det_type='Item'\n"
             + "  and(:branch = 0 or temp_tr_details.branch=:branch)\n"
            + "  and year(tr_date)=year(:date)\n"
            + "  and month(tr_date)=month(:date)", nativeQuery = true)
    public BigDecimal getMonthBasketValue(@Param("branch") Integer branch,@Param("date") String date);

}
