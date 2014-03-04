package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by jome on 2014/03/03.
 */
@Controller
public class SmsController {
    @Autowired
    UserService userService;
    @Autowired
    SmsService smsService;

    @RequestMapping("quicksms")
    public String sendBulkSmsForm(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "quicksms";

    }

    @RequestMapping("testsms")
    public String sendTestSms(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        Map<String,Object> map = smsService.sendSms("This is a test message from the java api");
        model.addAttribute("result", map);
        return "testsms";
    }
}
