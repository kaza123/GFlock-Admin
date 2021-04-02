
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports;

import com.supervision.wms.transaction.reports.model.MBalanceReport;
import com.supervision.wms.transaction.reports.model.MHourlyReport;
import com.supervision.wms.transaction.reports.model.MIPGReport;
import com.supervision.wms.transaction.reports.model.MInvoiceWise;
import com.supervision.wms.transaction.reports.model.MItemWise;
import com.supervision.wms.transaction.reports.model.MItemWiseGrn;
import com.supervision.wms.transaction.reports.model.MKPI;
import com.supervision.wms.transaction.reports.model.MSystemMix;
import com.supervision.wms.transaction.reports.model.MSystemSale;
import com.supervision.wms.transaction.reports.model.MTransactionSummary;
import com.supervision.wms.transaction.reports.model.PaymentSummary;
import com.supervision.wms.transaction.reports.model.TGrnSummary;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/sv/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    
    @RequestMapping(value = "/stock-balance" , method = RequestMethod.POST)
    public List<Object[]> stockBalance(@RequestBody MBalanceReport balanceReport){
        return reportService.stockBalance(balanceReport.getToDate(),balanceReport.getBranch(),balanceReport.getCategory(),balanceReport.getSubCategory(),balanceReport.getStyle());
    } 
    @RequestMapping(value = "/transaction-summary" , method = RequestMethod.POST)
    public List<Object[]> transactionSummary(@RequestBody MTransactionSummary transactionSummary){
        return reportService.transactionSummary(transactionSummary.getFromDate(),transactionSummary.getToDate(),transactionSummary.getBranch(),transactionSummary.getTransaction(),transactionSummary.getCategory(),transactionSummary.getStyle());
    } 
    @RequestMapping(value = "/grn-summary" , method = RequestMethod.POST)
    public List<Object[]> grnSummary(@RequestBody TGrnSummary grn){
        return reportService.grnSummary(grn.getFromDate(),grn.getToDate(),grn.getBranch(),grn.getTransactionNo(),grn.getReferenceNo(),grn.getSupplier(),grn.getStyle());
    } 
    @RequestMapping(value = "/invoice-wise" , method = RequestMethod.POST)
    public List<Object[]> invoiceWise(@RequestBody MInvoiceWise invWise){
        return reportService.invoiceWise(invWise.getFromDate(),invWise.getToDate(),invWise.getBranch(),invWise.getTerminal(),invWise.getType(),invWise.getReference());
    } 
    @RequestMapping(value = "/category-wise" , method = RequestMethod.POST)
    public List<Object[]> categoryWise(@RequestBody MItemWiseGrn model){
        return reportService.categoryWise(model.getFromDate(),model.getToDate(),model.getBranch(),model.getCategory(),model.getStyle());
    } 
    @RequestMapping(value = "/category-wise-grn" , method = RequestMethod.POST)
    public List<Object[]> categoryWiseGrn(@RequestBody MItemWiseGrn model){
        return reportService.categoryWiseGrn(model.getFromDate(),model.getToDate(),model.getBranch(),model.getCategory(),model.getStyle());
    } 
    @RequestMapping(value = "/style-wise" , method = RequestMethod.POST)
    public List<Object[]> styleWise(@RequestBody MItemWiseGrn model){
        return reportService.styleWise(model.getFromDate(),model.getToDate(),model.getBranch(),model.getStyle());
    } 
    @RequestMapping(value = "/item-wise" , method = RequestMethod.POST)
    public List<Object[]> itemWise(@RequestBody MItemWise itemWise){
        return reportService.itemWise(itemWise);
    } 
    @RequestMapping(value = "/item-wise_grn" , method = RequestMethod.POST)
    public List<Object[]> itemWise(@RequestBody MItemWiseGrn itemWiseGrn){
        
        System.out.println("Controller "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Object[]> itemWiseGrn1 = reportService.itemWiseGrn(itemWiseGrn);
        
        System.out.println(itemWiseGrn1.size()+" controller SIZE "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return itemWiseGrn1;
    } 
    @RequestMapping(value = "/system_sale" , method = RequestMethod.POST)
    public MSystemMix systemSales(@RequestBody MSystemSale systemSale){
        return reportService.systemSales(systemSale.getFromDate(),systemSale.getToDate(),systemSale.getCollection(),systemSale.getStyleLabel(),systemSale.getCategory());
    }
    
    
    @RequestMapping(value = "/pending-style" , method = RequestMethod.GET)
    public Object[] systemSales(){
        return reportService.getSales();
    } 
    
    @RequestMapping(value = "/pending-style-update/{style}" , method = RequestMethod.GET)
    public int updateStyle(@PathVariable String style){
        return reportService.updatePendingStyle(style);
    } 
    
    
    
    @RequestMapping(value = "/stock-balance-sub" , method = RequestMethod.POST)
    public List<Object[]> stockBalanceSub(@RequestBody MBalanceReport balanceReport){
        return reportService.stockBalanceSub(balanceReport.getFromDate(),balanceReport.getToDate(),balanceReport.getBranch(),balanceReport.getCategory(),balanceReport.getSubCategory(),balanceReport.getStyle(),balanceReport.getDescription());
    } 
    @RequestMapping(value = "/transaction-summary-sub" , method = RequestMethod.POST)
    public List<Object[]> transactionSummarySub(@RequestBody MTransactionSummary transactionSummary){
        return reportService.transactionSummarySub(transactionSummary.getFromDate(),transactionSummary.getToDate(),transactionSummary.getBranch(),transactionSummary.getTransaction(),transactionSummary.getCategory(),transactionSummary.getStyle(),transactionSummary.getTraIndex(),transactionSummary.getTraNumber());
    } 
    @RequestMapping(value = "/grn-summary-sub/{grnNo}/{style}" , method = RequestMethod.GET)
    public List<Object[]> grnSummarySub(@PathVariable Integer grnNo,@PathVariable String style){
        return reportService.grnSummarySub(grnNo,style);
    } 
    @RequestMapping(value = "/invoice-wise-sum/{invoice}" , method = RequestMethod.GET)
    public List<Object[]> invoiceSub(@PathVariable String invoice){
        return reportService.invoiceWiseSum(invoice);
    } 
    
    @RequestMapping(value = "/invoice-wise-payment/{invoice}" , method = RequestMethod.GET)
    public List<Object[]> stockBalancePayment(@PathVariable String invoice){
        return reportService.invoiceWisePayment(invoice);
    } 
    @RequestMapping(value = "/ipg_report" , method = RequestMethod.POST)
    public List<Object[]> stockBalancePayment(@RequestBody MIPGReport param){
        return reportService.ipgReport(param);
    } 
    @RequestMapping(value = "/hourly-report" , method = RequestMethod.POST)
    public List<Object[]> hourlyReport(@RequestBody MHourlyReport param){
        return reportService.hourlyReport(param);
    } 
    
    //payment summary
    @RequestMapping(value = "/payment-summary" , method = RequestMethod.POST)
    public HashMap<String, BigDecimal> paymentSummary(@RequestBody PaymentSummary paymentSummary){
        return reportService.paymentSummary(paymentSummary);
    } 
    
    //KPI
    @RequestMapping(value = "/sales_units_kpi_report" , method = RequestMethod.POST)
    public List<Object[]> salesUnitsKPIReport (@RequestBody MKPI mkpi){
        return reportService.salesUnitsKPIReport(mkpi);
    } 
    @RequestMapping(value = "/sales_unit_detailed_report_branch" , method = RequestMethod.POST)
    public List<Object[]> salesUnitDetailedReportBranch (@RequestBody MKPI mkpi){
       return reportService.salesUnitDetailedReportBranch(mkpi);
    } 
    @RequestMapping(value = "/sales_unit_department_report" , method = RequestMethod.POST)
    public List<Object[]> salesUnitDepartmentReport (@RequestBody MKPI mkpi){
       return reportService.salesUnitDepartmentReport(mkpi);
    } 
    @RequestMapping(value = "/sales_unit_designer_kpi_report" , method = RequestMethod.POST)
    public List<Object[]> salesUnitDesignerKpiReport (@RequestBody MKPI mkpi){
       return reportService.salesUnitDesignerReport(mkpi);
    } 
    
}
