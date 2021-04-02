/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.reports;

import com.supervision.wms.transaction.reports.model.MHourlyReport;
import com.supervision.wms.transaction.reports.model.MIPGReport;
import com.supervision.wms.transaction.reports.model.MItemWise;
import com.supervision.wms.transaction.reports.model.MItemWiseGrn;
import com.supervision.wms.transaction.reports.model.MKPI;
import com.supervision.wms.transaction.reports.model.MSystemDetail;
import com.supervision.wms.transaction.reports.model.MSystemMix;
import com.supervision.wms.transaction.reports.model.PaymentSummary;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kasun
 */
@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Object[]> stockBalance(
            String toDate,
            String branch,
            String category,
            String subCategory,
            String style) {

        if (null == toDate || "".equals(toDate)) {
            toDate = "0";
            throw new RuntimeException("toDate is required !");
        }
        if (null == branch || "".equals(branch)) {
            branch = "0";
        }
        if (null == category || "".equals(category)) {
            category = "0";
        }
        if (null == subCategory || "".equals(subCategory)) {
            subCategory = "0";
        }
        if (null == style || "".equals(style)) {
            style = "0";
        }

        return reportRepository.stockBalance(toDate, branch, category, subCategory, style);

    }

    public List<Object[]> transactionSummary(
            String fromDate,
            String toDate,
            String branch,
            String transaction,
            String category,
            String style) {

        if (null == toDate || "".equals(toDate)) {
            toDate = "0";
            throw new RuntimeException("toDate is required !");
        }
        if (null == fromDate || "".equals(fromDate)) {
            fromDate = "0";
            throw new RuntimeException("fromDate is required !");
        }
        if (null == branch || "".equals(branch)) {
            branch = "0";
        }
        if (null == category || "".equals(category)) {
            category = "0";
        }
        if (null == transaction || "".equals(transaction)) {
            transaction = "0";
        }
        if (null == style || "".equals(style)) {
            style = "0";
        }

        return reportRepository.transactionSummary(fromDate, toDate, branch, transaction, category, style);

    }

    public List<Object[]> grnSummary(
            String fromDate,
            String toDate,
            String branch,
            String traNo,
            String refNo,
            String supplier,
            String style) {

        if (null == toDate || "".equals(toDate)) {
            toDate = "0";
            throw new RuntimeException("toDate is required !");
        }
        if (null == fromDate || "".equals(fromDate)) {
            fromDate = "0";
            throw new RuntimeException("fromDate is required !");
        }
        if (null == branch || "".equals(branch)) {
            branch = "0";
        }
        if (null == traNo || "".equals(traNo)) {
            traNo = "0";
        }
        if (null == refNo || "".equals(refNo)) {
            refNo = "0";
        }
        if (null == supplier || "".equals(supplier)) {
            supplier = "0";
        }
        if (null == style || "".equals(style)) {
            style = "0";
        }
        return reportRepository.grnSummary(fromDate, toDate, branch, traNo, refNo, supplier, style);
    }

    public List<Object[]> stockBalanceSub(
            String fromDate,
            String toDate,
            String branch,
            String category,
            String subCategory,
            String style,
            String description) {

        if (null == fromDate || "".equals(fromDate)) {
            fromDate = "0";
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            toDate = "0";
            throw new RuntimeException("toDate is required !");
        }
        if (null == branch || "".equals(branch)) {
            branch = "0";
        }
        if (null == category || "".equals(category)) {
            category = "0";
        }
        if (null == subCategory || "".equals(subCategory)) {
            subCategory = "0";
        }
        if (null == style || "".equals(style)) {
            style = "0";
            throw new RuntimeException("style is required !");
        }
        if (null == description || "".equals(description)) {
            description = "0";
            throw new RuntimeException("description is required !");
        }

        return reportRepository.stockBalanceSub(fromDate, toDate, branch, category, subCategory, style, description);

    }

    public List<Object[]> transactionSummarySub(
            String fromDate,
            String toDate,
            String branch,
            String transaction,
            String category,
            String style,
            String traIndex,
            String traNumber) {

        if (null == fromDate || "".equals(fromDate)) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            throw new RuntimeException("toDate is required !");
        }
        if (null == branch || "".equals(branch)) {
            throw new RuntimeException("branch is required !");
        }
        if (null == category || "".equals(category)) {
            category = "0";
        }
        if (null == transaction || "".equals(transaction)) {
            throw new RuntimeException("branch is required !");
        }
        if (null == style || "".equals(style)) {
            style = "0";
            throw new RuntimeException("style is required !");
        }

        return reportRepository.transactionSummarySub(fromDate, toDate, branch, transaction, category, style, traIndex, traNumber);

    }

    public List<Object[]> invoiceWise(
            String fromDate,
            String toDate,
            String branch,
            String terminal,
            String type,
            String reference) {

        return reportRepository.invoiceWise(fromDate, toDate, branch, terminal, type, reference);

    }

    public List<Object[]> invoiceWiseSum(String invoice) {
        return reportRepository.invoicewiseSum(invoice);
    }

    public List<Object[]> invoiceWisePayment(String invoice) {
        return reportRepository.invoicewisePayment(invoice);
    }

    public List<Object[]> itemWise(MItemWise itemWise) {
        if (null == itemWise.getFromDate() || "".equals(itemWise.getFromDate())) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == itemWise.getToDate() || "".equals(itemWise.getToDate())) {
            throw new RuntimeException("toDate is required !");
        }

        itemWise.setIsType(false);
        if (itemWise.getTypeGift() != null || itemWise.getTypeItem() != null) {
            itemWise.setIsType(true);
        }
        return reportRepository.itemWise(
                itemWise.getFromDate(),
                itemWise.getToDate(),
                itemWise.getFromTime(),
                itemWise.getToTime(),
                itemWise.getBranch(),
                itemWise.getTerminal(),
                itemWise.getIsType(),
                itemWise.getTypeGift(),
                itemWise.getTypeItem(),
                itemWise.getBarcode(),
                itemWise.getStyle(),
                itemWise.getCategory(),
                itemWise.getSubCategory()
        );

    }

    public List<Object[]> itemWiseGrn(MItemWiseGrn itemWiseGrn) {
        if (null == itemWiseGrn.getFromDate() || "".equals(itemWiseGrn.getFromDate())) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == itemWiseGrn.getToDate() || "".equals(itemWiseGrn.getToDate())) {
            throw new RuntimeException("toDate is required !");
        }

        return reportRepository.itemWiseGrn(
                itemWiseGrn.getFromDate(),
                itemWiseGrn.getToDate(),
                itemWiseGrn.getBranch(),
                itemWiseGrn.getBarcode(),
                itemWiseGrn.getStyle(),
                itemWiseGrn.getCategory(),
                itemWiseGrn.getSubCategory(),
                itemWiseGrn.getTransactionNo(),
                itemWiseGrn.getSupplier()
        );
    }

    public HashMap<String, BigDecimal> paymentSummary(PaymentSummary paymentSummary) {
        HashMap<String, BigDecimal> map = new HashMap<>();

        map.put("itemGrossSale", reportRepository.itemGrossSales(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("itemReturn", reportRepository.itemReturn(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("itemDiscount", reportRepository.itemDiscount(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("otherDiscount", reportRepository.otherDiscount(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("giftVoucherSale", reportRepository.giftVoucherSales(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("returnNote", reportRepository.returnNote(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("creditNoteIssued", reportRepository.creditNoteIssued(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("returnExchange", reportRepository.returnExchange(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cardSaleVisa", reportRepository.cardSaleVisa(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cardSaleMaster", reportRepository.cardSaleMaster(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cardSaleAmex", reportRepository.cardSaleAmex(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("frimi", reportRepository.frimi(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("creditNoteRedeem", reportRepository.creditNoteRedeem(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("giftVoucherRedeem", reportRepository.GiftVoucherRedeem(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cashBalance", reportRepository.cashBalance(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cashBalance", reportRepository.cashBalance(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        map.put("cheque", reportRepository.cheques(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        System.out.println("from date : " + paymentSummary.getFromDate());
        System.out.println("to date : " + paymentSummary.getToDate());
        System.out.println("branch : " + paymentSummary.getBranch());
        System.out.println("terminal : " + paymentSummary.getTerminal());

        map.put("otherIncome", reportRepository.otherIncome(paymentSummary.getFromDate(), paymentSummary.getToDate(),
                paymentSummary.getBranch(), paymentSummary.getTerminal()));

        return map;
    }

    public List<Object[]> categoryWise(String fromDate, String toDate, String branch, String category, String style) {
        if (null == fromDate || "".equals(fromDate)) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            throw new RuntimeException("toDate is required !");
        }
        return reportRepository.getCategoryWise(fromDate, toDate, branch, category, style);
    }
    public List<Object[]> categoryWiseGrn(String fromDate, String toDate, String branch, String category, String style) {
        if (null == fromDate || "".equals(fromDate)) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            throw new RuntimeException("toDate is required !");
        }
        return reportRepository.getCategoryWiseGRN(fromDate, toDate, branch, category, style);
    }

    public List<Object[]> styleWise(String fromDate, String toDate, String branch, String style) {
        if (null == fromDate || "".equals(fromDate)) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            throw new RuntimeException("toDate is required !");
        }
        return reportRepository.getStyleWise(fromDate, toDate, branch, style);
    }

    public MSystemMix systemSales(String fromDate, String toDate, String collection, String style, String category) {
        if (null == fromDate || "".equals(fromDate)) {
            throw new RuntimeException("fromDate is required !");
        }
        if (null == toDate || "".equals(toDate)) {
            throw new RuntimeException("toDate is required !");
        }

        if (null == collection) {
            throw new RuntimeException("Collection No is required !");
        }

        MSystemMix mix = new MSystemMix();
        ArrayList<Object[]> styleList = reportRepository.styleList(collection, style, category);
        System.out.println("%%%%%%%%%%%%%%%");
        System.out.println("styleList size "+styleList.size());
        System.out.println("%%%%%%%%%%%%%%%");
        ArrayList<Object[]> branchList = reportRepository.branchList();
        System.out.println("%%%%%%%%%%%%%%%");
        System.out.println("branchList size "+branchList.size());
        System.out.println("%%%%%%%%%%%%%%%");
        ArrayList<MSystemDetail> detailList = new ArrayList<>();

        mix.setStyleList(styleList);
        mix.setColorList(styleList);
        mix.setBranchList(branchList);
        for (Object[] color : styleList) {
            Integer aSum = 0;
            Integer bSum = 0;
            Integer cSum = 0;
            Integer dSum = 0;
            Integer eSum = 0;
            Integer fSum = 0;
            Integer gSum = 0;
            Integer previousSum = 0;
            Integer balanceSum = 0;
            Integer inQtySum = 0;

            for (Object[] branch : branchList) {
                ArrayList<Object[]> detailListListSub = reportRepository.getDetail(color[0].toString(), color[1].toString(), branch[0].toString(), fromDate, toDate, category, color[3].toString());
                System.out.println("%%%%%%%%%%%%%%%");
                System.out.println(color[0].toString()+" - "+ color[1].toString()+" - "+ branch[0].toString()+" - "+ fromDate+" - "+ toDate+" - "+ category+" - "+ color[3].toString());
                System.out.println("detailListListSub "+detailListListSub.size());
                System.out.println("%%%%%%%%%%%%%%%");
                
                MSystemDetail systemDetail = new MSystemDetail();
                //empty data set
                systemDetail.setStyle(color[0].toString());
                systemDetail.setColor(color[1].toString());
                systemDetail.setCutQty(0);
                systemDetail.setBranchIndex(Integer.parseInt(branch[0].toString()));
                systemDetail.setBranchName(branch[1].toString());
                systemDetail.setGrnQty(0);
                systemDetail.setA(0);
                systemDetail.setB(0);
                systemDetail.setC(0);
                systemDetail.setD(0);
                systemDetail.setE(0);
                systemDetail.setF(0);
                systemDetail.setG(0);
                systemDetail.setPreviousOutQty(0);
                systemDetail.setBalanceQty(0);
                systemDetail.setBalanceQty(0);
                systemDetail.setPrice(new BigDecimal(color[2].toString()));

                for (Object[] detail : detailListListSub) {
                    systemDetail.setStyle(detail[0].toString());
                    systemDetail.setColor(detail[1].toString());
                    systemDetail.setCutQty(Integer.parseInt(detail[2].toString()));
                    systemDetail.setBranchIndex(Integer.parseInt(branch[0].toString()));
                    systemDetail.setBranchName(branch[1].toString());

                    if ("A".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        aSum += a;
                        systemDetail.setA(a);
                    }
                    if ("B".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        bSum += a;
                        systemDetail.setB(Integer.parseInt(detail[5].toString()));
                    }
                    if ("C".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        cSum += a;
                        systemDetail.setC(Integer.parseInt(detail[5].toString()));
                    }
                    if ("D".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        dSum += a;
                        systemDetail.setD(Integer.parseInt(detail[5].toString()));
                    }
                    if ("E".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        eSum += a;
                        systemDetail.setE(Integer.parseInt(detail[5].toString()));
                    }
                    if ("F".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        fSum += a;
                        systemDetail.setF(Integer.parseInt(detail[5].toString()));
                    }
                    if ("G".equals(detail[4].toString())) {
                        Integer a = Integer.parseInt(detail[5].toString());
                        gSum += a;
                        systemDetail.setG(Integer.parseInt(detail[5].toString()));
                    }
                    Integer totSold = systemDetail.getA()
                            + systemDetail.getB()
                            + systemDetail.getC()
                            + systemDetail.getD()
                            + systemDetail.getE()
                            + systemDetail.getF()
                            + systemDetail.getG();

                    systemDetail.setTotalSoldQty(totSold == null ? 0 : totSold);

                }
                Integer grnQty = reportRepository.getGrnQty(color[0].toString(), color[1].toString(), branch[0].toString(), category, color[3].toString());
                systemDetail.setGrnQty(grnQty);
                inQtySum += grnQty;

                Integer previousSoldQty = reportRepository.getPerviousSoldQty(color[0].toString(), color[1].toString(), branch[0].toString(), fromDate, category, color[3].toString());
                systemDetail.setPreviousOutQty(previousSoldQty);
                previousSum += previousSoldQty;

                System.out.println("color[4].toString() " +color[4].toString());
                System.out.println("color[1].toString() " +color[1].toString());
                Integer cutQty = reportRepository.getCutQty(color[4].toString(), color[1].toString());
                systemDetail.setCutQty(cutQty);
                detailList.add(systemDetail);

            }
            MSystemDetail systemDetail = new MSystemDetail();
            //empty data set
            systemDetail.setStyle(color[0].toString());
            systemDetail.setColor(color[1].toString());
            systemDetail.setCutQty(0);
            systemDetail.setBranchIndex(0);
            systemDetail.setBranchName("Total");
            systemDetail.setGrnQty(inQtySum);
            systemDetail.setA(aSum);
            systemDetail.setB(bSum);
            systemDetail.setC(cSum);
            systemDetail.setD(dSum);
            systemDetail.setE(eSum);
            systemDetail.setF(fSum);
            systemDetail.setG(gSum);
            systemDetail.setTotalSoldQty(0);
            systemDetail.setPreviousOutQty(previousSum);
            systemDetail.setBalanceQty(balanceSum);
            systemDetail.setPrice(new BigDecimal(color[2].toString()));
            detailList.add(systemDetail);

        }
        mix.setDetailList(detailList);
        return mix;
    }

    public Object[] getSales() {
        return reportRepository.getPendingStyle();
    }

    public int updatePendingStyle(String Style) {
        return reportRepository.updatePendingStyle(Style);

    }

    public List<Object[]> grnSummarySub(Integer grnNo, String style) {
        return reportRepository.getGrnSummarySub(grnNo, style);
    }

    public List<Object[]> salesUnitsKPIReport(MKPI mkpi) {
        String collection = mkpi.getCollection();
        String fromDate = mkpi.getFromDate();
        String toDate = mkpi.getToDate();
        String style = mkpi.getStyle();
        String category = mkpi.getCategory();
        String subCategory = mkpi.getSubCategory();
        String designer = mkpi.getDesigner();
        List<Object[]> list = reportRepository.getBasicKPI(collection, fromDate, toDate, style, category, subCategory, designer);

        list.forEach((object) -> {
            Integer costingId = Integer.parseInt(object[0].toString());
            String collectionDate = object[1].toString();

            object[10] = reportRepository.getGRNQty(costingId);
            object[11] = reportRepository.getSalesQty(costingId);
            object[15] = reportRepository.get1W(costingId, collectionDate);
            object[17] = reportRepository.get1M(costingId, collectionDate);
            object[19] = reportRepository.get2M(costingId, collectionDate);
            object[21] = reportRepository.get3M(costingId, collectionDate);

        });

        return list;
    }

    public List<Object[]> salesUnitDetailedReportBranch(MKPI mkpi) {
        String collection = mkpi.getCollection();
        String fromDate = mkpi.getFromDate();
        String toDate = mkpi.getToDate();
        String style = mkpi.getStyle();
        String category = mkpi.getCategory();
        String subCategory = mkpi.getSubCategory();
        String designer = mkpi.getDesigner();
        String branch = mkpi.getBranch();
        List<Object[]> list = reportRepository.salesUnitDetailedReportBranch(collection, fromDate, toDate, style, category, subCategory, designer, branch);
        list.forEach((object) -> {
            String paramCostingId = object[0].toString();
            String paramDate = object[1].toString();
            String paramBranch = object[7].toString();

            object[8] = reportRepository.getGRNQty(paramCostingId, paramBranch);
            object[9] = reportRepository.getSalesQty(paramCostingId, paramBranch);
            object[10] = reportRepository.get1W(paramCostingId, paramBranch, paramDate);
            object[11] = reportRepository.get1M(paramCostingId, paramBranch, paramDate);
            object[12] = reportRepository.get2M(paramCostingId, paramBranch, paramDate);
            object[13] = reportRepository.get3M(paramCostingId, paramBranch, paramDate);

        });
        return list;
    }

    public List<Object[]> salesUnitDepartmentReport(MKPI mkpi) {

        String collection = mkpi.getCollection();
        String fromDate = mkpi.getFromDate();
        String toDate = mkpi.getToDate();
        String style = mkpi.getStyle();
        String category = mkpi.getCategory();
        String subCategory = mkpi.getSubCategory();
        String designer = mkpi.getDesigner();
        List<Object[]> list = reportRepository.salesUnitDepartmentReport(collection, fromDate, toDate, style, category, subCategory, designer);

        list.forEach((object) -> {
            String department = object[0].toString();
            String collectionDate = object[1].toString();
            String collection1 = object[2].toString();

            object[3] = reportRepository.getOrderQty(department, collection1);
            object[4] = reportRepository.getCutQtyForDepartment(department, collection1);
            object[5] = reportRepository.getGRNQtyForDepartment(department, collection1);
            object[6] = reportRepository.getSalesQtyForDepartment(department, collection1);
            object[7] = reportRepository.get1WForDepartment(department, collection1, collectionDate);
            object[8] = reportRepository.get1MForDepartment(department, collection1, collectionDate);
            object[9] = reportRepository.get2MForDepartment(department, collection1, collectionDate);
            object[10] = reportRepository.get3MForDepartment(department, collection1, collectionDate);
        });
        return list;
    }

    public List<Object[]> salesUnitDesignerReport(MKPI mkpi) {
        String collection = mkpi.getCollection();
        String fromDate = mkpi.getFromDate();
        String toDate = mkpi.getToDate();
        String style = mkpi.getStyle();
        String category = mkpi.getCategory();
        String subCategory = mkpi.getSubCategory();
        String designer = mkpi.getDesigner();
        List<Object[]> list = reportRepository.salesUnitDesignerKpiReport(collection, fromDate, toDate, style, category, subCategory, designer);

        list.forEach((object) -> {
            String designer1 = object[0].toString();
            String date1 = object[2].toString();
            String collection1 = object[3].toString();
            String style1 = object[5].toString();
            
            object[6] = reportRepository.getOrderQtyDesigner(designer1,style1);
            object[7] = reportRepository.getCutQtyDesigner(designer1,style1);
            object[8] = reportRepository.getGRNQtyForDesigner(designer1,style1);
            object[9] = reportRepository.getSalesQtyForDesigner(designer1,style1);
            object[10] = reportRepository.get1WForDesigner(designer1,style1, date1);
            object[11] = reportRepository.get1MForDesigner(designer1,style1, date1);
            object[12] = reportRepository.get2MForDesigner(designer1,style1, date1);
            object[13] = reportRepository.get3MForDesigner(designer1,style1, date1);
            
        });
        return list;
    }

    public List<Object[]> ipgReport(MIPGReport param) {
        
        String fromDate = param.getfDate();
        String toDate = param.gettDate();
        
         return reportRepository.ipgReoprt(fromDate,toDate);
    }

    List<Object[]> hourlyReport(MHourlyReport param) {
        String fromDate = param.getfDate();
        String fTime = param.getfTime();
        String tTime = param.gettTime();
        int branch = param.getBranch();
        
     return reportRepository.hourlyReport(fromDate,fTime,tTime,branch);
        
    }

}
