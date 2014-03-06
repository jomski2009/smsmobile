package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.SMSRequest;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by jome on 2014/03/03.
 */
@Controller
public class SmsController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    SmsService smsService;

    @Autowired
    Configuration configuration;

    @RequestMapping(value = "quicksms", method = RequestMethod.GET)
    public String sendQuickSmsForm(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "quicksms";

    }

    @RequestMapping(value = "quicksms", method = RequestMethod.POST)
    public String sendQuickSms(Model model, @RequestParam("message") String message, @RequestParam("recipient-list") String recipients) {

        String[] recipientList = recipients.split("\\n");

        SendMessageResult messageResult = smsService.sendQuickSms(message, recipientList);
        System.out.println(messageResult);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("messageresult", messageResult);
        return "appcenter";

    }

    @RequestMapping("testsms")
    public String sendTestSms(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        Map<String, Object> map = smsService.sendSms("This is a test message from the java api");
        model.addAttribute("result", map);
        return "testsms";
    }
}
