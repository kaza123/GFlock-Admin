/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.plant;

import com.supervision.wms.transaction.job.loyalty_ledger.LoyaltyLedgerService;
import com.supervision.wms.transaction.job.loyalty_ledger.model.TLoyaltyLedger;
import com.supervision.wms.transaction.master.loyalty_customer.LoyaltyCustomerService;
import com.supervision.wms.transaction.master.loyalty_customer.model.MLoyaltyCustomer;
import com.supervision.wms.transaction.master.loyalty_setting.LoyaltySettingService;
import com.supervision.wms.transaction.master.loyalty_setting.model.MLoyaltySetting;
import com.supervision.wms.transaction.master.loyalty_type.LoyaltyTypeService;
import com.supervision.wms.transaction.master.plant.model.MPlant;
import com.supervision.wms.transaction.master.plant.model.PlantDetail;
import com.supervision.wms.transaction.master.sms.SmsRepository;
import com.supervision.wms.transaction.master.sms_text.SmsTextService;
import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
public class PlantService {

    @Autowired
    public PlantRepository plantRepository;

    @Autowired
    public LoyaltyCustomerService loyaltyCustomerService;

    @Autowired
    public LoyaltyTypeService loyaltyTypeService;

    @Autowired
    public LoyaltyLedgerService ledgerService;

    @Autowired
    public SmsRepository smsRepository;

    @Autowired
    public SmsTextService smsTextService;

    @Autowired
    public LoyaltySettingService loyaltySettingService;

    @Transactional
    public MPlant save(PlantDetail plantDetail) {
        if (null == plantDetail.getLoyaltyIndex() || plantDetail.getLoyaltyIndex() <= 0) {
            //new loyalty customer
            MLoyaltyCustomer loyaltyCustomer = new MLoyaltyCustomer();
            loyaltyCustomer.setLoyaltyNo(plantDetail.getMobileNo());
            loyaltyCustomer.setMobileNo(plantDetail.getMobileNo());
            loyaltyCustomer.setName(plantDetail.getName());
            loyaltyCustomer.setIsDelete(false);
            loyaltyCustomer.setAddress(plantDetail.getAddress());
            loyaltyCustomer.setCity(plantDetail.getCity());
            loyaltyCustomer.setEmail(plantDetail.getEmail());
            loyaltyCustomer.setIsSms(true);
            loyaltyCustomer.setBranchServerId(plantDetail.getBranch());
            loyaltyCustomer.setLoyaltyType(loyaltyTypeService.findByName("CUSTOMER").getIndexNo());
            loyaltyCustomer.setProStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRegDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loyaltyCustomer.setRecidance(plantDetail.getRecidance());
            Integer save = loyaltyCustomerService.save(loyaltyCustomer);
            plantDetail.setLoyaltyIndex(save);

            TLoyaltyLedger ledger = new TLoyaltyLedger();
            ledger.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ledger.setDiscountPre(BigDecimal.ZERO);
            ledger.setDiscountValue(BigDecimal.ZERO);
            ledger.setInvBranch(null);
            ledger.setInvDate(null);
            ledger.setInvNo(null);
            ledger.setItemQty(0);
            ledger.setItemValue(new BigDecimal(BigInteger.ZERO));
            ledger.setLoyaltyCostomer(save);
            ledger.setPointIn(new BigDecimal(25));
            ledger.setPointOut(new BigDecimal(0));
            ledger.setPointType("Normal_Point");
            ledger.setSmsDiscount(null);
            ledger.setType("point");
            ledger.setType2("point");
            TLoyaltyLedger save1 = ledgerService.save(ledger);
            System.out.println("ledger save");
            if (save1.getIndexNo() > 0) {
                //send sms
                MLoyaltySetting mLoyaltySetting = new MLoyaltySetting();
                mLoyaltySetting = loyaltySettingService.findByName("WELCOME_POINT");
                if (mLoyaltySetting.getName() == null || "".equals(mLoyaltySetting.getName())) {
                    throw new RuntimeException("Can't find welcome point !");
                }

                MSmsText smsText = smsTextService.findByName("SAVE_SUCCESS");
                String sendText = smsText.getText1() + " " + (int) mLoyaltySetting.getValue() + " " + smsText.getText2();

                sendRegistrationSms(loyaltyCustomer.getMobileNo(), sendText);
                System.out.println("sms send");
                
            } else {
                throw new RuntimeException("Normal point save fail!");
            }

        }
        MPlant plant = new MPlant();
        plant.setLoyaltyCustomer(plantDetail.getLoyaltyIndex());
        plant.setBranch(plantDetail.getBranch());
        plant.setPlantName(plantDetail.getPlantName());
        plant.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        plant.setUser(plantDetail.getUser());

        return plantRepository.save(plant);
    }

    public List<Object[]> getTop(Integer limit) {
        return plantRepository.getTop(limit);
    }

    public List<Object[]> getTop(Integer limit, String mobile) {
        return plantRepository.getTopByMobile(limit, mobile);
    }

    public HashMap getCount() {
        Integer count = plantRepository.getCount();
        HashMap<Integer, Integer> map = new HashMap();
        map.put(1, count);
        return map;
    }

    public List<Object> getPlants() {
        return plantRepository.getPlants();
    }

    public String findByName(String name) {
        return smsRepository.findByName(name).getValue();
    }

    private Integer sendRegistrationSms(String mobile, String text) {

        try {
            String message = text.replace(" ", "%20");
            String apikey = findByName("loyalty");
            String URL = "http://gflock-sms.supervisionglobal.com/send_sms.php?api_key=" + apikey + "&number=" + mobile + "&message=" + message;
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();

            return code;

        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
