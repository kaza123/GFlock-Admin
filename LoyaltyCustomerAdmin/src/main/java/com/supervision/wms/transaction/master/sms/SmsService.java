/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.sms;

import com.supervision.wms.transaction.master.sms_text.SmsTextRepository;
import com.supervision.wms.transaction.master.sms_text.model.MSmsText;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author kasun
 */
@Service
public class SmsService {

    @Autowired
    public SmsRepository smsRepository;

    @Autowired
    public SmsTextRepository smsTextRepository;

    public String findByName(String name) {
        return smsRepository.findByName(name).getValue();
    }

    public String getRandomNo() {
        Random r = new Random();
        String otp = "";
        String numbers = "123456789";

        for (int i = 0; i < 5; i++) {
            otp += numbers.charAt(r.nextInt(numbers.length()));
        }
        return otp;
    }

    public Integer sendSms(String mobile, String message) {
//        try {
            message = message.replace("%0a", System.lineSeparator());
            String apikey = findByName("loyalty");
//        String URL = "http://gflock-sms.supervisionglobal.com/send_sms.php?api_key=" + apikey + "&number=" + mobile + "&message=" + message;
        String URL = "http://gflock-sms.supervisionglobal.com/send_sms.php?api_key=" + apikey + "&number=" + mobile + "&message=" + message;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL, String.class);
        if ("0".equals(result)) {
            return 200;
        }
        return -1;

    };

    public String resentOtp(String mobile, String otp) {
        MSmsText smsText = smsTextRepository.findByName("OTP_SENT");
        String text = smsText.getText1() + " " + otp + " " + smsText.getText2();

        Integer respond = sendSms(mobile, text);
        if (respond == 0 || respond == 200) {
            return otp;
        }
        return null;
    }

}
