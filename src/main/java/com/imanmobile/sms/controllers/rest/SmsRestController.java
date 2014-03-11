package com.imanmobile.sms.controllers.rest;

import com.imanmobile.sms.domain.BulkMessageDTO;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.services.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jome on 2014/03/07.
 */
@RestController
@RequestMapping("api/v1/sms")
public class SmsRestController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SmsService smsService;

    @RequestMapping("test")
    public ResponseEntity<Map<String, Object>> testData(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "quicksms")
    public ResponseEntity<SendMessageResult> sendQuickSms(@RequestParam("message") String message, @RequestParam("recipient-list") String recipients){
        String[] recipientList = recipients.split("\\n");

        SendMessageResult messageResult = smsService.sendQuickSms(message, recipientList);

        return new ResponseEntity<>(messageResult, HttpStatus.OK);

    }

    @RequestMapping(value = "bulksms", method = RequestMethod.POST)
    public HttpEntity processbulkSMS(Model model,@RequestBody BulkMessageDTO messageDTO ) {

        logger.info(messageDTO.toString());
        smsService.sendBulkSms(messageDTO);

        return new ResponseEntity<BulkMessageDTO>(messageDTO, HttpStatus.CREATED);
    }
}
