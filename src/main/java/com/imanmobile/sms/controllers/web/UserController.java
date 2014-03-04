package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.services.UserService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

}
