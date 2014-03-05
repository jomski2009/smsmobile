package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.Account;
import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jome on 2014/02/28.
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Configuration configuration;


    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "user-signup";
    }

    @RequestMapping(value = "user/create", method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user, Model model) {
        //Do server side validation of the user model.

        String userKey = userService.createUser(user);

        model.addAttribute("user", user);
        model.addAttribute("userKey", userKey);
        return "user-created";
    }

    @RequestMapping(value = "account-settings", method = RequestMethod.GET)
    public String getAccountForUser(Model model) {
        //Do server side validation of the user model.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        SMSClient client = new SMSClient(configuration);
        Account account = client.getCustomerProfileClient().getCustomerAccount();

        model.addAttribute("user", user);
        model.addAttribute("account", account);

        return "account-settings";
    }


}
