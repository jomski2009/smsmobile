package com.imanmobile.sms.services;

import com.imanmobile.sms.domain.BulkMessageDTO;
import com.imanmobile.sms.oneapi.model.SendMessageResult;

import java.util.Map;

/**
 * Created by jome on 2014/03/04.
 */
public interface SmsService {
    Map<String, Object> sendSms(String message);

    SendMessageResult sendQuickSms(String message, String[] recipients);
    SendMessageResult sendBulkSms(BulkMessageDTO bulkMessage);
}
