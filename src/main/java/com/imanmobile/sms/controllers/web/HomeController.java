package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by jome on 2014/02/28.
 */

@Controller
public class HomeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("message", "Bulk Messaging Solution from ImanMobile");
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        logger.info("Entering login method for get");
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String doLogout(Model model,  HttpServletRequest request) {
        logger.info("Logging out current user");

        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping(value = "/appcenter")
    public String appcenter(Model model, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getUserPrincipal().getName();
        logger.info("Logging current user: " + username);
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        double balance = user.getAccount().getAccountBalance().getBalance();
        model.addAttribute("balance", balance);
        logger.info("App Center calls");
        return "appcenter";
    }


    @RequestMapping(value = "/processlogin")
    public String processLogin(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Processing login information");
        String username = request.getUserPrincipal().getName();
        logger.info("Logging current user: " + username);

        return "home";
    }


}
