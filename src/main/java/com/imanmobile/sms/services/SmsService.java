package com.imanmobile.sms.services;

import java.util.Map;

/**
 * Created by jome on 2014/03/04.
 */
public interface SmsService {
    Map<String, Object> sendSms(String message);
}
