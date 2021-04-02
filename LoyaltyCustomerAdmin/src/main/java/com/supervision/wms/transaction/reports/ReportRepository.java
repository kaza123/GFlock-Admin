/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports;

import com.supervision.wms.transaction.reports.model.PosMItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
public interface ReportRepository extends JpaRepository<PosMItem, Integer> {

    @Query(value = "select pos_m_item.image_code as style,\n"
            + "	TRIM(pos_m_item.print_description),\n"
            + "	TRIM(pos_m_item.category),\n"
            + "	TRIM(pos_m_item.sub_category),\n"
            + "	sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty) as balance,\n"
            + " main_m_branch.name_code, \n"
            + " main_m_branch.index_no, \n"
            + " pos_m_item.collection_no \n"
            + "from main_t_stock_ledger\n"
            + "inner JOIN pos_m_item on pos_m_item.index_no=main_t_stock_ledger.item	\n"
            + "inner JOIN main_m_branch on main_m_branch.index_no=main_t_stock_ledger.branch \n"
            + "where  main_t_stock_ledger.transaction_date<=:toDate\n"
            + "	and (:branch = '0' or main_t_stock_ledger.branch=:branch)\n"
            + "	and (:category = '0' or pos_m_item.category=:category )\n"
            + "	and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	and (:style = '0' or pos_m_item.image_code=:style )\n"
            + "group by main_m_branch.index_no, pos_m_item.image_code,pos_m_item.print_description\n"
            + "having balance !=0", nativeQuery = true)
    public List<Object[]> stockBalance(
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("style") String style
    );

    @Query(value = "select main_t_stock_ledger.`transaction`,\n"
            + "		 main_t_stock_ledger.transaction_index,\n"
            + "		 main_t_stock_ledger.transaction_date,\n"
            + "		 main_t_stock_ledger.transaction_number,\n"
            + "		main_t_stock_ledger.branch as branch_index,\n"
            + "		 main_m_branch.name as branch_name,\n"
            + "		 sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty) as qty \n"
            + "      from main_t_stock_ledger\n"
            + "      inner JOIN main_m_branch on main_m_branch.index_no=main_t_stock_ledger.branch \n"
            + "      inner JOIN pos_m_item on pos_m_item.index_no=main_t_stock_ledger.item\n"
            + "		where main_t_stock_ledger.transaction_date>=:fromDate \n"
            + "		and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "		and (:branch = '0' or main_t_stock_ledger.branch=:branch)\n"
            + "		and (:transaction = '0' or main_t_stock_ledger.`transaction`=:transaction)\n"
            + "		and (:category = '0' or pos_m_item.category=:category)\n"
            + "		and (:style = '0' or pos_m_item.image_code=:style)\n"
            + "      group by main_t_stock_ledger.transaction_index, main_t_stock_ledger.transaction_number", nativeQuery = true)
    public List<Object[]> transactionSummary(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("transaction") String transaction,
            @Param("category") String category,
            @Param("style") String style
    );

    @Query(value = "SELECT main_m_branch.name_code AS branch,\n"
            + "	main_t_grn.transaction_date AS DATE,\n"
            + "	m_grn_vehicle.NAME AS vehicle,\n"
            + "	main_m_supplier.NAME AS supplier,\n"
            + "	sum(main_t_grn_item.amount), \n"
            + "	main_t_grn.transaction_ref_number  AS ref_no,\n"
            + "	main_t_grn.transaction_number AS tra_no,\n"
            + "	main_t_grn.is_sync,\n"
            + "	main_t_grn.index_no,\n"
            + "	pos_m_item.image_code AS style,\n"
            + "	sum(main_t_grn_item.qty) AS qty  \n"
            + "FROM main_t_grn\n"
            + "LEFT JOIN main_m_branch ON main_m_branch.index_no=main_t_grn.branch\n"
            + "LEFT JOIN m_grn_vehicle ON m_grn_vehicle.index_no=main_t_grn.vehicle\n"
            + "LEFT JOIN main_m_supplier ON main_m_supplier.index_no=main_t_grn.supplier\n"
            + "LEFT JOIN main_t_grn_item ON main_t_grn_item.grn=main_t_grn.index_no\n"
            + "LEFT JOIN pos_m_item ON pos_m_item.index_no=main_t_grn_item.item\n"
            + "WHERE DATE_FORMAT(main_t_grn.transaction_date,'%Y-%m-%d')>=:fromDate\n"
            + "AND  DATE_FORMAT(main_t_grn.transaction_date,'%Y-%m-%d')<=:toDate\n"
            + "AND (:referenceNo='0' OR  main_t_grn.transaction_ref_number =:referenceNo)\n"
            + "AND (:branch='0' OR  main_t_grn.branch =:branch)\n"
            + "AND (:supplier='0' OR  main_t_grn.supplier =:supplier)\n"
            + "AND (:style='0' OR  pos_m_item.image_code =:style)\n"
            + "AND (:transactionNo='0' OR  main_t_grn.transaction_number =:transactionNo)\n"
            + "GROUP BY main_t_grn.index_no,pos_m_item.image_code\n"
            + "ORDER BY main_t_grn.index_no desc,pos_m_item.image_code", nativeQuery = true)
    public List<Object[]> grnSummary(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("transactionNo") String transactionNo,
            @Param("referenceNo") String referenceNo,
            @Param("supplier") String supplier,
            @Param("style") String style
    );

    @Query(value = "select pos_t_transaction_summary.terminal_id,\n"
            + "	pos_t_transaction_summary.branch,\n"
            + "	pos_t_transaction_summary.invoice_no,\n"
            + "	concat(pos_t_transaction_summary.tr_date, \" \",pos_t_transaction_summary.tr_end_time) as tr_date,\n"
            + "	pos_t_transaction_summary.tr_type,\n"
            + "	sum(pos_t_transaction_details.item_value) as item_value,\n"
            + "	pos_t_payment_summary.discount_amount AS line_discount,\n"
            + "	sum(pos_t_transaction_details.final_value) as final_value\n"
            + "from pos_t_transaction_summary\n"
            + "left join pos_t_transaction_details on pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "INNER JOIN pos_t_payment_summary ON pos_t_payment_summary.tr_index_no=pos_t_transaction_summary.index_no \n"
            + "where pos_t_transaction_summary.tr_date>=:fromDate and pos_t_transaction_summary.tr_date<=:toDate\n"
            + "and pos_t_transaction_summary.branch=:branch\n"
            + "and(:terminal = '0' or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "and(:reference = '0' or pos_t_transaction_summary.invoice_no=:reference)\n"
            + "and(:type = '0' or pos_t_transaction_summary.tr_type=:type)\n"
            + "group by pos_t_transaction_summary.index_no \n"
            + "order by pos_t_transaction_summary.tr_date ,pos_t_transaction_summary.tr_end_time", nativeQuery = true)
    public List<Object[]> invoiceWise(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal,
            @Param("type") String type,
            @Param("reference") String reference
    );

    /**
     *
     * @param fromDate
     * @param toDate
     * @param branch
     * @param category
     * @param subCategory
     * @param style
     * @param description
     * @return object array
     * @query select item.barcode, item.details,
     *
     * (select
     * ifnull(sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty),0.00)
     * from main_t_stock_ledger left join pos_m_item on pos_m_item.index_no=
     * main_t_stock_ledger.item where pos_m_item.barcode=item.barcode and main_t_stock_ledger.transaction_date<'2018-12-01'
     * and ('0' = '0' or main_t_stock_ledger.branch=0) # from date
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as open_qty,
     *
     * (select ifnull(sum(main_t_stock_ledger.in_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and main_t_stock_ledger.transaction_date<='2019-01-02'
     * and main_t_stock_ledger.transaction= 'TRANSACTION_GRN'
     * and ('0' = '0' or main_t_stock_ledger.branch=0)
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as grn_qty,
     *
     * (select ifnull(sum(main_t_stock_ledger.out_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and main_t_stock_ledger.transaction_date<='2019-01-02'
     * and main_t_stock_ledger.transaction= 'INVOICE'
     * and ('0' = '0' or main_t_stock_ledger.branch=0)
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as inv_qty,
     *
     * (select ifnull(sum(main_t_stock_ledger.in_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and main_t_stock_ledger.transaction_date<='2019-01-02'
     * and main_t_stock_ledger.transaction= 'RETURN'
     * and ('0' = '0' or main_t_stock_ledger.branch=0)
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as return_qty,
     *
     * (select ifnull(sum(main_t_stock_ledger.in_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and main_t_stock_ledger.transaction_date<='2019-01-02'
     * and (main_t_stock_ledger.transaction= 'TRANSACTION_STOCK_TRANSFER' )
     * and ('0' = '0' or main_t_stock_ledger.branch=0)
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as transfer_in_qty,
     *
     *
     * (select ifnull(sum(main_t_stock_ledger.out_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and main_t_stock_ledger.transaction_date<='2019-01-02'
     * and (main_t_stock_ledger.transaction= 'TRANSACTION_STOCK_TRANSFER' )
     * and ('0' = '0' or main_t_stock_ledger.branch=0)
     * and ('0' = '0' or pos_m_item.category=null )
     * and ('0' = '0' or pos_m_item.sub_category=null )
     * ) as transfer_out_qty,
     *
     * (select ifnull(sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty),0.00)
     * from main_t_stock_ledger
     * left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item
     * where pos_m_item.barcode=item.barcode
     * and main_t_stock_ledger.transaction_date>='2018-12-01' and
     * main_t_stock_ledger.transaction_date<='2019-01-02' and
     * (main_t_stock_ledger.transaction= 'TRANSACTION_ADJUSTMENT' ) and ('0' =
     * '0' or main_t_stock_ledger.branch=0) and ('0' = '0' or
     * pos_m_item.category=null ) and ('0' = '0' or pos_m_item.sub_category=null
     * ) ) as adjustment_qty
     *
     *
     * from main_t_stock_ledger left join pos_m_item item on
     * item.index_no=main_t_stock_ledger.item where item.image_code ='035 Wintex
     * Cotton' and item.print_description='GFM035 NC SHIRT' group by
     * item.barcode
     *
     */
    @Query(value = "select item.barcode,\n"
            + "	item.details,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "	and main_t_stock_ledger.transaction_date<:fromDate\n"
            + "	 and (:branch = '0' or main_t_stock_ledger.branch=:branch) # from date\n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as open_qty,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.in_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and main_t_stock_ledger.transaction= 'TRANSACTION_GRN'\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as grn_qty,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.out_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and main_t_stock_ledger.transaction= 'INVOICE'\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as inv_qty,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.in_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and main_t_stock_ledger.transaction= 'RETURN'\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as return_qty,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.in_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and (main_t_stock_ledger.transaction= 'TRANSACTION_STOCK_TRANSFER' )\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as transfer_in_qty,\n"
            + "	\n"
            + "		\n"
            + "		(select ifnull(sum(main_t_stock_ledger.out_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and (main_t_stock_ledger.transaction= 'TRANSACTION_STOCK_TRANSFER' )\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as transfer_out_qty,\n"
            + "	\n"
            + "	(select ifnull(sum(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty),0.00)\n"
            + "	from main_t_stock_ledger\n"
            + "	left join pos_m_item on pos_m_item.index_no= main_t_stock_ledger.item \n"
            + "	where pos_m_item.barcode=item.barcode \n"
            + "			and main_t_stock_ledger.transaction_date>=:fromDate and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "			and (main_t_stock_ledger.transaction= 'TRANSACTION_ADJUSTMENT' )\n"
            + "	 		and (:branch = '0' or main_t_stock_ledger.branch=:branch) \n"
            + "         and (:category = '0' or pos_m_item.category=:category )\n"
            + "         and (:subCategory = '0' or pos_m_item.sub_category=:subCategory )\n"
            + "	) as adjustment_qty\n"
            + "	\n"
            + "\n"
            + "from 	main_t_stock_ledger\n"
            + "left join pos_m_item  item on item.index_no=main_t_stock_ledger.item\n"
            + "where item.image_code =:style and item.print_description=:description \n"
            + "group by item.barcode", nativeQuery = true)
    public List<Object[]> stockBalanceSub(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("style") String style,
            @Param("description") String description
    );

    @Query(value = "select pos_m_item.index_no,\n"
            + "		pos_m_item.image_code as style,\n"
            + "		pos_m_item.barcode,\n"
            + "		pos_m_item.details,\n"
            + "		pos_m_item.print_description,\n"
            + "		main_t_stock_ledger.in_qty,\n"
            + "		main_t_stock_ledger.out_qty\n"
            + "from main_t_stock_ledger\n"
            + "        inner JOIN main_m_branch on main_m_branch.index_no=main_t_stock_ledger.branch \n"
            + "        inner JOIN pos_m_item on pos_m_item.index_no=main_t_stock_ledger.item\n"
            + "where main_t_stock_ledger.transaction_date>=:fromDate \n"
            + "		and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "		and (main_t_stock_ledger.branch=:branch)\n"
            + "		and (main_t_stock_ledger.`transaction`=:transaction)\n"
            + "		and (:category = '0' or pos_m_item.category=:category)\n"
            + "		and (:style = '0' or pos_m_item.image_code=:style)\n"
            + "		and (main_t_stock_ledger.transaction_index=:traIndex )\n"
            + "		and (main_t_stock_ledger.transaction_number=:traNumber)\n"
            + "order by pos_m_item.image_code", nativeQuery = true)
    public List<Object[]> transactionSummarySub(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("transaction") String transaction,
            @Param("category") String category,
            @Param("style") String style,
            @Param("traIndex") String traIndex,
            @Param("traNumber") String traNumber
    );

    @Query(value = "select pos_t_transaction_details.tr_det_type,\n"
            + "	pos_t_transaction_details.bar_code,\n"
            + "	pos_t_transaction_details.description,\n"
            + "	ifnull(pos_m_item.category,'- - -') as category,\n"
            + "	ifnull(pos_m_item.image_code,'- - -') as style,\n"
            + "	pos_t_transaction_details.item_qty,\n"
            + "	pos_t_transaction_details.item_price,\n"
            + "	pos_t_transaction_details.item_value,\n"
            + "	pos_t_transaction_details.line_dis_amt1+pos_t_transaction_details.line_dis_amt2 as line_discount,\n"
            + "	pos_t_transaction_details.final_value\n"
            + "from pos_t_transaction_details\n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no\n"
            + "left join pos_m_item on pos_m_item.barcode=pos_t_transaction_details.bar_code\n"
            + "where pos_t_transaction_summary.invoice_no=:invoice", nativeQuery = true)
    public List<Object[]> invoicewiseSum(@Param("invoice") String invoice);

    @Query(value = "select pos_t_payment_summary.invoice_amount,\n"
            + "	pos_t_payment_summary.discount_rate,\n"
            + "	pos_t_payment_summary.discount_amount,\n"
            + "	pos_t_payment_summary.final_value,\n"
            + "	pos_t_payment_summary.total_paid_amount,\n"
            + "	pos_t_payment_summary.balance_return,\n"
            + "	pos_t_payment_summary.credit_note_no,\n"
            + "	pos_t_payment_summary.credit_note_amount,\n"
            + "	pos_t_payment_summary.ignore_amount,\n"
            + "	pos_t_payment_details.pmt_type,\n"
            + "	pos_t_payment_details.pmt_ref_no,\n"
            + "	pos_t_payment_details.pmt_amount,\n"
            + "	pos_t_payment_details.machine_no\n"
            + "from pos_t_payment_summary\n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "left join pos_t_payment_details on pos_t_payment_details.pmt_index_no=pos_t_payment_summary.index_no\n"
            + "where pos_t_transaction_summary.invoice_no=:invoice", nativeQuery = true)
    public List<Object[]> invoicewisePayment(@Param("invoice") String invoice);

    @Query(value = "SELECT pos_t_transaction_details.bar_code,\n"
            + "	pos_t_transaction_details.tr_det_type as type,\n"
            + "	if(pos_t_transaction_details.tr_det_type ='Gift',concat('Gift Voucher - ',pos_t_transaction_summary.invoice_no),pos_t_transaction_details.description) as description,\n"
            + "	pos_m_item.image_code as style,\n"
            + "	pos_m_item.category,\n"
            + "	pos_m_item.sub_category,\n"
            + "	sum(pos_t_transaction_details.item_qty) as qty,\n"
            + "	pos_t_transaction_details.item_value  as value,\n"
            + "	sum(pos_t_transaction_details.line_dis_amt1+pos_t_transaction_details.line_dis_amt2) as discount,\n"
            + "	sum(pos_t_transaction_details.final_value) as final_value\n"
            + "from pos_t_transaction_details\n"
            + "LEFT join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no\n"
            + "LEFT join pos_m_item on pos_m_item.barcode=pos_t_transaction_details.bar_code\n"
            + "where pos_t_transaction_summary.tr_date>=:fromDate and pos_t_transaction_summary.tr_date<=:toDate\n"
            + "	and (:branch is null or pos_t_transaction_summary.branch=:branch)\n"
            + "	and (:fromTime is null or pos_t_transaction_summary.tr_end_time>=:fromTime)\n"
            + "	and (:toTime is null or pos_t_transaction_summary.tr_end_time<=:toTime)\n"
            + "	and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "	and (:isType = false or pos_t_transaction_details.tr_det_type in (:typeGift,:typeItem)) \n"
            + "	and (:barcode is null or pos_t_transaction_details.bar_code = :barcode) \n"
            + "	and (:style is null or pos_m_item.image_code = :style) \n"
            + "	and (:category is null or pos_m_item.category = :category)\n"
            + "	and (:subCategory is null or pos_m_item.sub_category = :subCategory)\n"
            + "GROUP by pos_t_transaction_details.bar_code,pos_t_transaction_details.tr_det_type\n"
            + "order by pos_t_transaction_details.bar_code,pos_t_transaction_details.tr_det_type", nativeQuery = true)
    public List<Object[]> itemWise(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("fromTime") String fromTime,
            @Param("toTime") String toTime,
            @Param("branch") Integer branch,
            @Param("terminal") Integer terminal,
            @Param("isType") Boolean isType,
            @Param("typeGift") String typeGift,
            @Param("typeItem") String typeItem,
            @Param("barcode") String barcode,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory);

    @Query(value = "CALL sp_item_wise_grn(:fromDate,:toDate,:branch,:barcode,:style,:category,:subCategory,:traNo,:supplier);", nativeQuery = true)
    public List<Object[]> itemWiseGrn(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("barcode") String barcode,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("traNo") String traNo,
            @Param("supplier") String supplier
    );

    // payment summary
    @Query(value = "Select ifnull(sum(item_value),0.00) as total_item_value\n"
            + "     from temp_tr_details \n"
            + "     Where tr_det_type='Item' and tr_type='invoice'\n"
            + "	  and tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	  and tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "     and (:terminal is null or terminal_id=:terminal)\n"
            + "     and (:branch is null or branch=:branch)", nativeQuery = true)
    public BigDecimal itemGrossSales(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pos_t_payment_summary.ignore_amount),0.00) as visa \n"
            + "from pos_t_payment_summary \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "Where  pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal otherIncome(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull(sum(pos_t_transaction_details.final_value),0.00) as return_note\n"
            + "   from pos_t_transaction_details\n"
            + "   left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no\n"
            + "   where pos_t_transaction_summary.tr_type='return' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal itemReturn(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull(sum(pos_t_payment_summary.discount_amount),0.00)  as val\n"
            + "    from pos_t_payment_summary\n"
            + "    left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "    where pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	 and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "    and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "    and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal itemDiscount(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(line_dis_amt1+line_dis_amt2),0.00) as discount\n"
            + "     from temp_tr_details \n"
            + "     Where tr_type='invoice' \n"
            + "     and tr_det_type='Item' \n"
            + "     and tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	  and tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "     and (:terminal is null or terminal_id=:terminal)\n"
            + "     and (:branch is null or branch=:branch)", nativeQuery = true)
    public BigDecimal otherDiscount(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull(sum(pos_t_transaction_details.final_value),0.00) as gv_sales\n"
            + "    from pos_t_transaction_details\n"
            + "    left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no\n"
            + "    where pos_t_transaction_details.tr_det_type='gift' and pos_t_transaction_summary.tr_type='invoice' \n"
            + "    and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	 and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "    and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "    and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal giftVoucherSales(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull(sum(pos_t_transaction_details.final_value),0.00) as return_note\n"
            + "   from pos_t_transaction_details\n"
            + "   left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no\n"
            + "   where pos_t_transaction_summary.tr_type='return' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal returnNote(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull(sum(pos_t_payment_summary.credit_note_amount),0.00)  as val\n"
            + "  from pos_t_payment_summary\n"
            + "  left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "  where pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	 and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "    and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "    and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal creditNoteIssued(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "select ifnull((Select ifnull(sum(pmt_amount),0.00) as Cash_value \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='CASH' \n"
            + " and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch))-\n"
            + " (select sum(pos_t_payment_summary.balance_return)  as val\n"
            + "  from pos_t_payment_summary\n"
            + "  left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "  where pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch))-"
            + "(select sum(pos_t_payment_summary.cash_return)  as val\n"
            + "  from pos_t_payment_summary\n"
            + "  left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=pos_t_payment_summary.tr_index_no\n"
            + "  where pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)),0.00) as val", nativeQuery = true)
    public BigDecimal cashBalance(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where (pmt_type='VISA' or pmt_type='CUP') \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal cardSaleVisa(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='MASTER' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal cardSaleMaster(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='AMEX' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal cardSaleAmex(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='FRIMI' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal frimi(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='GV' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal GiftVoucherRedeem(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='CN' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal creditNoteRedeem(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(pmt_amount),0.00) as visa \n"
            + "from temp_payment_details \n"
            + "left join pos_t_transaction_summary on pos_t_transaction_summary.index_no=temp_payment_details.index_no\n"
            + "Where pmt_type='CHEQUE' \n"
            + "   and pos_t_transaction_summary.tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	and pos_t_transaction_summary.tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "   and (:terminal is null or pos_t_transaction_summary.terminal_id=:terminal)\n"
            + "   and (:branch is null or pos_t_transaction_summary.branch=:branch)", nativeQuery = true)
    public BigDecimal cheques(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select ifnull(sum(tr_final_value),0.00) as return_exchange_value from temp_tr_details \n"
            + "     Where tr_status=2 and tr_type='invoice' and tr_det_type='Return' \n"
            + "     and tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "	  and tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "     and (:terminal is null or terminal_id=:terminal)\n"
            + "     and (:branch is null or branch=:branch)", nativeQuery = true)
    public BigDecimal returnExchange(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("terminal") String terminal
    );

    @Query(value = "Select pos_m_item.category,\n"
            + "    sum(temp_tr_details.item_qty) as qty ,\n"
            + "    ifnull(sum(tr_final_value),0.00) as total_item_value\n"
            + "    from temp_tr_details \n"
            + "    left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "    Where tr_det_type='Item'\n"
            + "    and tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "    and tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "    and(:branch is null or temp_tr_details.branch=:branch)\n"
            + "    and (:category is null or pos_m_item.category=:category)\n"
            + "    and (:style is null or pos_m_item.image_code=:style)\n"
            + "    group by pos_m_item.category", nativeQuery = true)
    public List<Object[]> getCategoryWise(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("category") String category,
            @Param("style") String style);

    @Query(value = "SELECT pos_m_item.category,\n"
            + "	sum(main_t_grn_item.qty) AS qty,\n"
            + "	sum(main_t_grn_item.amount) AS total_item_value\n"
            + "FROM main_t_grn\n"
            + "INNER JOIN main_t_grn_item ON main_t_grn_item.grn=main_t_grn.index_no\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=main_t_grn_item.item\n"
            + "WHERE DATE_FORMAT(main_t_grn.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "AND DATE_FORMAT(main_t_grn.transaction_date,'%Y-%m-%d')<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "AND (:branch is null or main_t_grn.branch=:branch)\n"
            + "AND (:category is null or pos_m_item.category=:category)\n"
            + "AND (:style is null or pos_m_item.image_code=:style)\n"
            + "GROUP BY pos_m_item.category", nativeQuery = true)
    public List<Object[]> getCategoryWiseGRN(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("category") String category,
            @Param("style") String style);

    @Query(value = "Select pos_m_item.image_code,\n"
            + "    sum(temp_tr_details.item_qty) as qty ,\n"
            + "    ifnull(sum(tr_final_value),0.00) as total_item_value\n"
            + "    from temp_tr_details \n"
            + "    left join pos_m_item on pos_m_item.barcode=temp_tr_details.bar_code \n"
            + "    Where tr_det_type='Item'\n"
            + "    and tr_date>=DATE_FORMAT(:fromDate,'%Y-%m-%d')\n"
            + "    and tr_date<=DATE_FORMAT(:toDate,'%Y-%m-%d')\n"
            + "    and(:branch is null or temp_tr_details.branch=:branch)\n"
            + "    and (:style is null or pos_m_item.image_code=:style)\n"
            + "    group by pos_m_item.image_code", nativeQuery = true)
    public List<Object[]> getStyleWise(
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("style") String style);
//    06389 bandaragama

    @Query(value = "select pos_m_item.image_code as style,\n"
            + "      pos_m_item.style_color ,\n"
            + "      pos_m_item.price as price,\n"
            + "      pos_m_item.collection_no as collection,\n"
            + "      pos_m_item.intCostingId \n"
            + "from pos_m_item\n"
            + "where (:style is null or pos_m_item.image_code=:style)\n"
            + "and (:collection is null or pos_m_item.collection_no=:collection)\n"
            + "and (:category is null or pos_m_item.category=:category)\n"
            + "group by pos_m_item.image_code , pos_m_item.style_color", nativeQuery = true)
    public ArrayList<Object[]> styleList(
            @Param("collection") String collection,
            @Param("style") String style,
            @Param("category") String category
    );

    @Query(value = "select main_m_branch.index_no,\n"
            + "	main_m_branch.name\n"
            + "from main_m_branch\n"
            + "where main_m_branch.`type`='OTHER'", nativeQuery = true)
    public ArrayList<Object[]> branchList();

    @Query(value = "select pos_m_item.image_code,\n"
            + "	pos_m_item.style_color,\n"
            + "	0 as cut_qty,\n"
            + "	0 as in_qty,\n"
            + "	main_m_size.group_id,\n"
            + "cast(sum(main_t_stock_ledger.out_qty)-sum(main_t_stock_ledger.in_qty) AS int) as sold_qty,\n"
            + "	0 as previous_sold,\n"
            + "	0 as balance_qty\n"
            + "from pos_m_item	\n"
            + "left join main_t_stock_ledger on main_t_stock_ledger.item=pos_m_item.index_no\n"
            + "left join main_m_branch on main_m_branch.index_no=main_t_stock_ledger.branch\n"
            + "left join main_m_size on main_m_size.size=pos_m_item.style_size \n"
            + "where main_t_stock_ledger.transaction_date>=:fromDate\n"
            + "and main_t_stock_ledger.transaction_date<=:toDate\n"
            + "and pos_m_item.image_code =:style\n"
            + "and pos_m_item.style_color =:color\n"
            + "and pos_m_item.collection_no =:collection \n"
            + "and main_m_branch.index_no =:branch \n"
            + "and (:category is null or pos_m_item.category=:category) \n"
            + "and (main_t_stock_ledger.`transaction`='invoice' or main_t_stock_ledger.`transaction`='return')\n"
            + "group by pos_m_item.style_size", nativeQuery = true)
    public ArrayList<Object[]> getDetail(
            @Param("style") String style,
            @Param("color") String color,
            @Param("branch") String branch,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("category") String category,
            @Param("collection") String collection);

    @Query(value = "select CAST(ifnull(sum(main_t_stock_ledger.in_qty),0) AS UNSIGNED) as in_qty\n"
            + "from main_t_stock_ledger\n"
            + "left join pos_m_item on pos_m_item.index_no=main_t_stock_ledger.item\n"
            + "where pos_m_item.image_code=:style\n"
            + "and pos_m_item.style_color=:color\n"
            + "and pos_m_item.collection_no=:collection\n"
            + "and main_t_stock_ledger.branch=:branch\n"
            + "and (:category is null or pos_m_item.category=:category)\n"
            + "and  main_t_stock_ledger.`transaction`= 'TRANSACTION_GRN'", nativeQuery = true)
    public Integer getGrnQty(
            @Param("style") String style,
            @Param("color") String color,
            @Param("branch") String branch,
            @Param("category") String category,
            @Param("collection") String collection
    );

    @Query(value = "select CAST(ifnull(sum(main_t_stock_ledger.in_qty)-sum(main_t_stock_ledger.out_qty),0) AS int) as qty\n"
            + "from main_t_stock_ledger\n"
            + "            left join pos_m_item on pos_m_item.index_no=main_t_stock_ledger.item\n"
            + "            where pos_m_item.image_code=:style\n"
            + "            and pos_m_item.style_color=:color\n"
            + "            and pos_m_item.collection_no=:collection\n"
            + "            and main_t_stock_ledger.branch=:branch\n"
            + "            and (:category is null or pos_m_item.category=:category)\n"
            + "            and main_t_stock_ledger.transaction_date <:fromDate\n"
            + "            AND main_t_stock_ledger.`transaction`<>'TRANSACTION_GRN'", nativeQuery = true)
    public Integer getPerviousSoldQty(
            @Param("style") String style,
            @Param("color") String color,
            @Param("branch") String branch,
            @Param("fromDate") String fromDate,
            @Param("category") String category,
            @Param("collection") String collection
    );

    @Query(value = "select style_style.index_no,\n"
            + "	style_style.style_name,\n"
            + "	style_style.collection_no\n"
            + "from style_style\n"
            + "where style_style.is_image=0", nativeQuery = true)
    public Object[] getPendingStyle();

    @Transactional
    @Modifying
    @Query(value = "UPDATE style_style SET style_style.is_image=1\n"
            + "WHERE  style_style.style_name=:style", nativeQuery = true)
    public int updatePendingStyle(
            @Param("style") String Style);

    @Query(value = "select ifnull(sum(m_cut_qty.qty),0.00) as qty\n"
            + "from m_cut_qty\n"
            + "where m_cut_qty.costingID=:cID\n"
            + "and m_cut_qty.color=:color",
            nativeQuery = true)
    public Integer getCutQty(
            @Param("cID") String costingID,
            @Param("color") String color
    );

    @Query(value = "SELECT pos_m_item.barcode,\n"
            + "	pos_m_item.details,\n"
            + "	pos_m_item.image_code,\n"
            + "	pos_m_item.category,\n"
            + "	pos_m_item.sub_category,\n"
            + "	pos_m_item.style_color,\n"
            + "	pos_m_item.style_size,\n"
            + "	pos_m_item.collection_no,\n"
            + "	main_t_grn_item.price,\n"
            + "	main_t_grn_item.qty,\n"
            + "	main_t_grn_item.amount\n"
            + "FROM main_t_grn_item\n"
            + "LEFT JOIN pos_m_item ON pos_m_item.index_no=main_t_grn_item.item\n"
            + "WHERE main_t_grn_item.grn=:grnNo and pos_m_item.image_code=:style", nativeQuery = true)
    public List<Object[]> getGrnSummarySub(
            @Param("grnNo") Integer grnNo,
            @Param("style") String style
    );

    @Query(value = "SELECT  m_collection_detail.costing_id,\n"
            + "	DATE_FORMAT( m_collection_detail.collection_start_date,'%Y-%m-%d') AS collection_date,\n"
            + "	pos_m_item.collection_no ,\n"
            + "	pos_m_item.category AS department,\n"
            + "	pos_m_item.sub_category,\n"
            + "	m_collection_detail.style,\n"
            + "	m_collection_detail.order_qty,\n"
            + "	m_collection_detail.cut_qty,\n"
            + "	'' AS damage_qty,\n"
            + "	'' AS damage_p,\n"
            + "	'' AS grn_qty,\n"
            + "	'' AS total_sales,\n"
            + "	'' AS sales_p,\n"
            + "	'' AS balance,\n"
            + "	'' AS balance_p,\n"
            + "	'' AS 1w,\n"
            + "	'' AS 1w_p,\n"
            + "	'' AS 1m,\n"
            + "	'' AS 1m_p,\n"
            + "	'' AS 2m,\n"
            + "	'' AS 2m_p,\n"
            + "	'' AS 3m,\n"
            + "	'' AS 3m_p\n"
            + "from m_collection_detail,pos_m_item\n"
            + "where pos_m_item.intCostingId=m_collection_detail.costing_id\n"
            + "and (:fromDate is NULL or m_collection_detail.collection_start_date>=:fromDate) \n"
            + " AND (:toDate is NULL or m_collection_detail.collection_start_date<=:toDate)\n"
            + "	AND (:collection is NULL or pos_m_item.collection_no=:collection)\n"
            + "	AND (:style IS NULL OR m_collection_detail.style=:style)\n"
            + "	AND (:designer IS NULL OR m_collection_detail.designer_id=:designer)\n"
            + "	AND (:category IS NULL OR pos_m_item.category=:category)\n"
            + "	AND (:subCategory IS NULL OR pos_m_item.sub_category=:subCategory)\n"
            + "GROUP BY m_collection_detail.costing_id\n"
            + "ORDER BY m_collection_detail.collection_start_date,pos_m_item.collection_no",
            nativeQuery = true
    )
    public List<Object[]> getBasicKPI(
            @Param("collection") String collection,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("designer") String designer);

    @Query(value = "SELECT ifnull(sum(main_t_grn_item.qty) ,0) AS grnQty\n"
            + "FROM main_t_grn_item,pos_m_item\n"
            + "where pos_m_item.index_no=main_t_grn_item.item\n"
            + "and pos_m_item.intCostingId=:costingId",
            nativeQuery = true)
    public Integer getGRNQty(@Param("costingId") Integer costingId);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_details,pos_m_item\n"
            + "where pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "and pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:costingId",
            nativeQuery = true)
    public Integer getSalesQty(@Param("costingId") Integer costingId);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_summary,pos_t_transaction_details,pos_m_item\n"
            + "where pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "and pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "and pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:costingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 7 DAY)",
            nativeQuery = true)
    public Integer get1W(@Param("costingId") Integer costingId,
            @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty) ,0)AS sales_qty\n"
            + "from pos_t_transaction_summary,pos_t_transaction_details,pos_m_item\n"
            + "where pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "and pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "and pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:costingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 1 month)",
            nativeQuery = true)
    public Integer get1M(@Param("costingId") Integer costingId,
            @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty) ,0)AS sales_qty\n"
            + "from pos_t_transaction_summary,pos_t_transaction_details,pos_m_item\n"
            + "where pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "and pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "and pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:costingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 2 month)",
            nativeQuery = true)
    public Integer get2M(@Param("costingId") Integer costingId,
            @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty) ,0)AS sales_qty\n"
            + "from pos_t_transaction_summary,pos_t_transaction_details,pos_m_item\n"
            + "where pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "and pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "and pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:costingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 3 month)",
            nativeQuery = true)
    public Integer get3M(@Param("costingId") Integer costingId,
            @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT  m_collection_detail.costing_id,\n"
            + " DATE_FORMAT( m_collection_detail.collection_start_date,'%Y-%m-%d') AS collection_date,\n"
            + " pos_m_item.collection_no ,\n"
            + " pos_m_item.category AS department,\n"
            + " pos_m_item.sub_category,\n"
            + " m_collection_detail.style,\n"
            + " main_m_branch.NAME AS branch,\n"
            + " main_m_branch.index_no AS branch_index,\n"
            + " '' as grnQty,\n"
            + " '' as salesQty,\n"
            + " '' as 1w,\n"
            + " '' as 1m,\n"
            + " '' as 2m,\n"
            + " '' as 3m\n"
            + " from m_collection_detail\n"
            + " inner JOIN  pos_m_item ON pos_m_item.intCostingId=m_collection_detail.costing_id\n"
            + " inner JOIN  main_m_branch ON main_m_branch.index_no\n"
            + " WHERE (:fromDate is NULL or m_collection_detail.collection_start_date>=:fromDate) \n"
            + " AND (:toDate is NULL or m_collection_detail.collection_start_date<=:toDate)\n"
            + " AND (:collection is NULL or pos_m_item.collection_no=:collection)\n"
            + " AND (:style IS NULL OR m_collection_detail.style=:style)\n"
            + " AND (:designer IS NULL OR m_collection_detail.designer_id=:designer)\n"
            + " AND (:category IS NULL OR pos_m_item.category=:category)\n"
            + " AND (:subCategory IS NULL OR pos_m_item.sub_category=:subCategory) \n"
            + " AND (:branch IS NULL OR main_m_branch.index_no=:branch) \n"
            + " AND (main_m_branch.`type`='other')\n"
            + " GROUP BY m_collection_detail.costing_id,main_m_branch.index_no\n"
            + " ORDER BY m_collection_detail.collection_start_date,pos_m_item.collection_no,m_collection_detail.style",
            nativeQuery = true)
    public List<Object[]> salesUnitDetailedReportBranch(
            @Param("collection") String collection,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("designer") String designer,
            @Param("branch") String branch);

    @Query(value = "SELECT ifnull(sum(main_t_grn_item.qty) ,0) AS grnQty\n"
            + " FROM main_t_grn_item\n"
            + " INNER JOIN pos_m_item a ON a.index_no=main_t_grn_item.item\n"
            + " INNER JOIN main_t_grn ON main_t_grn.index_no=main_t_grn_item.grn\n"
            + " WHERE a.intCostingId=:paramCostingId AND main_t_grn.branch=:paramBranch",
            nativeQuery = true)
    public Object getGRNQty(@Param("paramCostingId") String paramCostingId, @Param("paramBranch") String paramBranch);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_details\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "INNER JOIN pos_t_transaction_summary ON pos_t_transaction_summary.index_no=pos_t_transaction_details.tr_index_no \n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:paramCostingId \n"
            + "AND pos_t_transaction_summary.branch=:paramBranch",
            nativeQuery = true)
    public Object getSalesQty(@Param("paramCostingId") String paramCostingId, @Param("paramBranch") String paramBranch);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_summary\n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:paramCostingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:paramDate,'%Y-%m-%d'), INTERVAL 7 DAY)\n"
            + "AND pos_t_transaction_summary.branch=:paramBranch",
            nativeQuery = true)
    public Integer get1W(
            @Param("paramCostingId") String paramCostingId,
            @Param("paramBranch") String paramBranch,
            @Param("paramDate") String paramDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_summary\n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:paramCostingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:paramDate,'%Y-%m-%d'), INTERVAL 1 MONTH)\n"
            + "AND pos_t_transaction_summary.branch=:paramBranch",
            nativeQuery = true)
    public Integer get1M(
            @Param("paramCostingId") String paramCostingId,
            @Param("paramBranch") String paramBranch,
            @Param("paramDate") String paramDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_summary\n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:paramCostingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:paramDate,'%Y-%m-%d'), INTERVAL 2 MONTH)\n"
            + "AND pos_t_transaction_summary.branch=:paramBranch",
            nativeQuery = true)
    public Integer get2M(
            @Param("paramCostingId") String paramCostingId,
            @Param("paramBranch") String paramBranch,
            @Param("paramDate") String paramDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty\n"
            + "from pos_t_transaction_summary\n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no\n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:paramCostingId\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:paramDate,'%Y-%m-%d'), INTERVAL 3 MONTH)\n"
            + "AND pos_t_transaction_summary.branch=:paramBranch",
            nativeQuery = true)
    public Integer get3M(
            @Param("paramCostingId") String paramCostingId,
            @Param("paramBranch") String paramBranch,
            @Param("paramDate") String paramDate);

    @Query(value = "SELECT pos_m_item.category AS department,\n"
            + "DATE_FORMAT( m_collection_detail.collection_start_date,'%Y-%m-%d') AS collection_date, \n"
            + "pos_m_item.collection_no, \n"
            + "'' as orderQty, \n"
            + "'' as cutQty,  \n"
            + "'' as grnQty, \n"
            + "'' as salesQty,\n"
            + "'' as 1w, \n"
            + "'' as 1m, \n"
            + "'' as 2m, \n"
            + "'' as 3m, \n"
            + "'1' as order_by \n"
            + "from m_collection_detail \n"
            + "inner JOIN  pos_m_item ON pos_m_item.intCostingId=m_collection_detail.costing_id \n"
            + "WHERE (:fromDate is NULL or m_collection_detail.collection_start_date>=:fromDate)  \n"
            + "AND (:toDate is NULL or m_collection_detail.collection_start_date<=:toDate) \n"
            + "AND (:collection is NULL or pos_m_item.collection_no=:collection) \n"
            + "AND (:style IS NULL OR m_collection_detail.style=:style) \n"
            + "AND (:designer IS NULL OR m_collection_detail.designer_id=:designer) \n"
            + "AND (:category IS NULL OR pos_m_item.category=:category) \n"
            + "AND (:subCategory IS NULL OR pos_m_item.sub_category=:subCategory)\n"
            + "GROUP BY pos_m_item.category,pos_m_item.collection_no\n"
            + "ORDER BY pos_m_item.category,pos_m_item.collection_no",
            nativeQuery = true)
    public List<Object[]> salesUnitDepartmentReport(
            @Param("collection") String collection,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("designer") String designer);

    @Query(value = "SELECT sum(m_collection_detail.order_qty) AS qty\n"
            + "FROM m_collection_detail\n"
            + "INNER JOIN pos_m_item ON pos_m_item.intCostingId=m_collection_detail.costing_id\n"
            + "WHERE pos_m_item.category=:department\n"
            + "AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer getOrderQty(
            @Param("department") String department, @Param("collection") String collection);

    @Query(value = "SELECT sum(m_collection_detail.cut_qty) AS qty\n"
            + "FROM m_collection_detail\n"
            + "INNER JOIN pos_m_item ON pos_m_item.intCostingId=m_collection_detail.costing_id\n"
            + "WHERE pos_m_item.category=:department\n"
            + "AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer getCutQtyForDepartment(@Param("department") String department, @Param("collection") String collection);

    @Query(value = "SELECT ifnull(sum(main_t_grn_item.qty) ,0) AS grnQty \n"
            + "FROM main_t_grn_item \n"
            + "INNER JOIN pos_m_item a ON a.index_no=main_t_grn_item.item \n"
            + "INNER JOIN main_t_grn ON main_t_grn.index_no=main_t_grn_item.grn \n"
            + "WHERE a.category=:department AND a.collection_no=:collection",
            nativeQuery = true)
    public Integer getGRNQtyForDepartment(@Param("department") String department, @Param("collection") String collection);

    @Query(value = "SELECT ifnull(sum(pos_t_transaction_details.item_qty),0) AS sales_qty \n"
            + "from pos_t_transaction_details \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.category=:department  \n"
            + "AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer getSalesQtyForDepartment(@Param("department") String department, @Param("collection") String collection);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty \n"
            + " from pos_t_transaction_summary \n"
            + " INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no \n"
            + " INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + " WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.category=:department \n"
            + " AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 7 DAY) \n"
            + " AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer get1WForDepartment(@Param("department") String department, @Param("collection") String collection, @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty \n"
            + " from pos_t_transaction_summary \n"
            + " INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no \n"
            + " INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + " WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.category=:department \n"
            + " AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 1 MONTH) \n"
            + " AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer get1MForDepartment(@Param("department") String department, @Param("collection") String collection, @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty \n"
            + " from pos_t_transaction_summary \n"
            + " INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no \n"
            + " INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + " WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.category=:department \n"
            + " AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 2 MONTH) \n"
            + " AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer get2MForDepartment(@Param("department") String department, @Param("collection") String collection, @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty \n"
            + " from pos_t_transaction_summary \n"
            + " INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no \n"
            + " INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + " WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.category=:department \n"
            + " AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:collectionDate,'%Y-%m-%d'), INTERVAL 3 MONTH) \n"
            + " AND pos_m_item.collection_no=:collection",
            nativeQuery = true)
    public Integer get3MForDepartment(@Param("department") String department, @Param("collection") String collection, @Param("collectionDate") String collectionDate);

    @Query(value = "SELECT m_collection_detail.designer_id,\n"
            + "m_collection_detail.designer_name,\n"
            + "DATE_FORMAT( m_collection_detail.collection_start_date,'%Y-%m-%d') AS collection_date,  \n"
            + "pos_m_item.collection_no,  \n"
            + "pos_m_item.image_code,\n"
            + "pos_m_item.intCostingId,    \n"
            + "'' as orderQty,  \n"
            + "'' as cutQty,   \n"
            + "'' as grnQty,  \n"
            + "'' as salesQty, \n"
            + "'' as 1w,  \n"
            + "'' as 1m,  \n"
            + "'' as 2m,  \n"
            + "'' as 3m \n"
            + "from m_collection_detail  \n"
            + "inner JOIN  pos_m_item ON pos_m_item.intCostingId=m_collection_detail.costing_id  \n"
            + "WHERE (:fromDate is NULL or m_collection_detail.collection_start_date>=:fromDate)  \n"
            + "AND (:toDate is NULL or m_collection_detail.collection_start_date<=:toDate) \n"
            + "AND (:collection is NULL or pos_m_item.collection_no=:collection) \n"
            + "AND (:style IS NULL OR m_collection_detail.style=:style) \n"
            + "AND (:designer IS NULL OR m_collection_detail.designer_id=:designer) \n"
            + "AND (:category IS NULL OR pos_m_item.category=:category) \n"
            + "AND (:subCategory IS NULL OR pos_m_item.sub_category=:subCategory) \n"
            + "GROUP BY m_collection_detail.designer_id,pos_m_item.collection_no,pos_m_item.image_code \n"
            + "ORDER BY  m_collection_detail.designer_id,pos_m_item.collection_no,pos_m_item.image_code",
            nativeQuery = true)
    public List<Object[]> salesUnitDesignerKpiReport(
            @Param("collection") String collection,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("style") String style,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("designer") String designer);

    @Query(value = "SELECT m_collection_detail.order_qty AS qty \n"
            + "FROM m_collection_detail \n"
            + "WHERE m_collection_detail.costing_id=:style\n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer getOrderQtyDesigner(@Param("designer") String designer, @Param("style") String style);

    @Query(value = "SELECT sum(m_collection_detail.cut_qty) AS qty\n"
            + "FROM m_collection_detail\n"
            + "WHERE m_collection_detail.costing_id=:style\n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer getCutQtyDesigner(@Param("designer") String designer, @Param("style") String style);

    @Query(value = "SELECT ifnull(sum(main_t_grn_item.qty) ,0) AS grnQty \n"
            + "FROM main_t_grn_item \n"
            + "INNER JOIN pos_m_item  ON pos_m_item.index_no=main_t_grn_item.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_m_item.intCostingId=:style AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer getGRNQtyForDesigner(@Param("designer") String designer, @Param("style") String style);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty  \n"
            + "FROM pos_t_transaction_summary  \n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no  \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:style\n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer getSalesQtyForDesigner(@Param("designer") String designer, @Param("style") String style);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty  \n"
            + "FROM pos_t_transaction_summary  \n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no  \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:style\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:date,'%Y-%m-%d'), INTERVAL 7 DAY)  \n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer get1WForDesigner(@Param("designer") String designer, @Param("style") String style, @Param("date") String date);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty  \n"
            + "FROM pos_t_transaction_summary  \n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no  \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:style\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:date,'%Y-%m-%d'), INTERVAL 1 MONTH)  \n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer get1MForDesigner(@Param("designer") String designer, @Param("style") String style, @Param("date") String date);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty  \n"
            + "FROM pos_t_transaction_summary  \n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no  \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:style\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:date,'%Y-%m-%d'), INTERVAL 2 MONTH)  \n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer get2MForDesigner(@Param("designer") String designer, @Param("style") String style, @Param("date") String date);

    @Query(value = "SELECT IFNULL(sum(pos_t_transaction_details.item_qty),0) AS sales_qty  \n"
            + "FROM pos_t_transaction_summary  \n"
            + "INNER JOIN pos_t_transaction_details ON pos_t_transaction_details.tr_index_no=pos_t_transaction_summary.index_no  \n"
            + "INNER JOIN pos_m_item ON pos_m_item.index_no=pos_t_transaction_details.item \n"
            + "INNER JOIN m_collection_detail ON m_collection_detail.costing_id=pos_m_item.intCostingId\n"
            + "WHERE pos_t_transaction_details.tr_det_type='item' AND pos_m_item.intCostingId=:style\n"
            + "AND pos_t_transaction_summary.tr_date<=DATE_ADD(DATE_FORMAT(:date,'%Y-%m-%d'), INTERVAL 3 MONTH)  \n"
            + "AND m_collection_detail.designer_id=:designer",
            nativeQuery = true)
    public Integer get3MForDesigner(@Param("designer") String designer, @Param("style") String style, @Param("date") String date);

    @Query(value = "SELECT invoice_no,tr_date,d_name,pmt_type,\n"
            + "Shipping,final_value\n"
            + "FROM Online_Delivery_Details\n"
            + "WHERE tr_date>=:fromDate AND tr_date<=:toDate",
            nativeQuery = true)
    public List<Object[]> ipgReoprt(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT \n"
            + "v_hourly_report.bar_code,\n"
            + "v_hourly_report.print_code,\n"
            + "v_hourly_report.description,\n"
            + "SUM(v_hourly_report.item_qty) AS qty,\n"
            + "SUM(v_hourly_report.final_value) AS final_value,\n"
            + "SUM(v_hourly_report.discount_value) AS dis_value,\n"
            + "SUM(v_hourly_report.discounted_value) AS discounted_value,\n"
            + "round(SUM(v_hourly_report.final_value)/SUM(v_hourly_report.item_qty),2)  AS unit_price,\n"
            + "(SELECT SUM(main_t_stock_ledger.in_qty-main_t_stock_ledger.out_qty)\n"
            + "	FROM main_t_stock_ledger\n"
            + "	WHERE main_t_stock_ledger.item=v_hourly_report.index_no\n"
            + "	AND main_t_stock_ledger.branch=v_hourly_report.branch\n"
            + "	AND main_t_stock_ledger.date_time<:toDateTime) AS bal_qty\n"
            + "FROM v_hourly_report\n"
            + "WHERE v_hourly_report.date_time>=:fromDateTime\n"
            + "AND v_hourly_report.date_time<=:toDateTime\n"
            + "AND v_hourly_report.branch=:branch \n"
            + "GROUP BY \n"
            + "v_hourly_report.bar_code,\n"
            + "v_hourly_report.print_code,\n"
            + "v_hourly_report.description",
            nativeQuery = true)
    public List<Object[]> hourlyReport(
            @Param("fromDateTime") String fromDateTime,
            @Param("toDateTime") String toDateTime,
            @Param("branch") Integer branch);

}
