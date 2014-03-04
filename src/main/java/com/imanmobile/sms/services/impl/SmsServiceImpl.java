package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.services.SmsService;
import oneapi.client.impl.SMSClient;
import oneapi.config.Configuration;
import oneapi.model.SMSRequest;
import oneapi.model.SendMessageResult;
import oneapi.model.common.DeliveryInfoList;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jome on 2014/03/04.
 */

@Service
public class SmsServiceImpl implements SmsService {
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
}
