package com.imanmobile.sms.services.impl;

import com.google.gson.Gson;
import com.imanmobile.sms.oneapi.exception.RequestException;
import com.imanmobile.sms.oneapi.model.LoginRequest;
import com.imanmobile.sms.oneapi.model.common.LoginResponse;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.SMSRequest;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.oneapi.model.common.DeliveryInfoList;
import com.ning.http.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jome on 2014/03/04.
 */

@Service
public class SmsServiceImpl implements SmsService {
    private String apiUrl = "https://oneapi.infobip.com/";
    private String versionOneAPISMS = "1/";
    private String CUSTOMER_PROFILE = "customerProfile";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate template = new RestTemplate();

    @Override
    public Map<String, Object> sendSms(String message) {
        Map<String, Object> outcome = new HashMap<>();
        Configuration configuration = new Configuration("allangray", "wordpass15");
        SMSClient smsClient = new SMSClient(configuration);

        SMSRequest request = new SMSRequest("MyAllanGray", message, "tel:+27837930939");
        request.setClientCorrelator("secondjavamessage");
//        String[] address = new String[]{"tel:+27837930939"};
//        request.setAddress(address);
        SendMessageResult sendMessageResult = smsClient.getSMSMessagingClient().sendSMS(request);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DeliveryInfoList deliveryInfoList = smsClient.getSMSMessagingClient().queryDeliveryStatus("MyAllanGray", sendMessageResult.getClientCorrelator());
        String deliveryStatus = deliveryInfoList.getDeliveryInfo().get(0).toString();

        System.out.println(deliveryStatus);
        outcome.put("msgresult", sendMessageResult);
        outcome.put("status", deliveryStatus);
        return outcome;

    }

//    @Override
//    public LoginResponse login(String username, String password) throws UnsupportedEncodingException {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setPassword(password);
//        loginRequest.setUsername(username);
//        String authString = "Basic " + new String(org.apache.commons.codec.binary.Base64.encodeBase64((username + ":" + password).getBytes("UTF-8")));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //headers.set("Authorization", authString);
//        Gson gson = new Gson();
//        String request = gson.toJson(loginRequest);
//        HttpEntity entity = new HttpEntity(request, headers);
//        String url = apiUrl+versionOneAPISMS+CUSTOMER_PROFILE+"/login";
//        logger.info("Request sent is: {}", request);
//        logger.info("Called url: {}", url);
//        logger.info(entity.getHeaders().toString());
//        ResponseEntity<LoginResponseWrapper> response = template.exchange(url, HttpMethod.POST, entity, LoginResponseWrapper.class);
//        logger.info("Response: {}",response.getBody().getLogin());
//        return response.getBody().getLogin();
//    }
}
