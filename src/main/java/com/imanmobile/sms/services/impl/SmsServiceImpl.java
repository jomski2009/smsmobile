package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.domain.Account;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.SMSRequest;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.oneapi.model.common.AccountBalance;
import com.imanmobile.sms.oneapi.model.common.DeliveryInfoList;
import com.imanmobile.sms.services.SmsService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jome on 2014/03/04.
 */

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    Datastore ds;

    @Autowired
    Configuration configuration;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Override
    public SendMessageResult sendQuickSms(String message, String[] recipients) {
        int arLength = recipients.length;
        String[] reclist = new String[arLength];


        for(int i =0; i<arLength;i++){
            reclist[i] = recipients[i].trim();
        }
        for (String r : recipients) {
            logger.info("Recipients: {}", r);
        }


        SMSClient client = new SMSClient(configuration);
        String senderId = client.getCustomerProfileClient().getCustomerAccount().getDefaultSender();

        SMSRequest smsRequest = new SMSRequest(senderId, message, reclist);
        //SMSRequest request = new SMSRequest("senderAddress","message to be sent","clientcorrelator","notifyurl","sendername","callbackdata", reclist);
        SMSRequest request = new SMSRequest(senderId,message,"testrelator",null,senderId,null, reclist);
        SendMessageResult messageResult = client.getSMSMessagingClient().sendSMS(request);
        ds.save(messageResult);

        //We should update the balance here, right?
        String accountKey = client.getCustomerProfileClient().getCustomerAccount().getKey();
        double balance = client.getCustomerProfileClient().getAccountBalance().getBalance();
        logger.info("New balance: {}", balance);

        UpdateOperations<Account> updateOperations = ds.createUpdateOperations(Account.class).set("accountBalance.balance", balance);
        UpdateResults<Account> updateResults = ds.update(ds.createQuery(Account.class).field("_id").equal(accountKey), updateOperations);


        return messageResult;
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
