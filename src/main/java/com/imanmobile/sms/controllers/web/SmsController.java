package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.Group;
import com.imanmobile.sms.domain.GroupResource;
import com.imanmobile.sms.domain.Recipient;
import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.services.ContactsService;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.services.UserService;
import org.apache.commons.lang3.text.WordUtils;
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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
    ContactsService contactsService;

    @Autowired
    Configuration configuration;

    @RequestMapping(value = "quicksms", method = RequestMethod.GET)
    public String sendQuickSmsForm(Model model) {

        addCurrentUserToModel(model);
        return "quicksms";
    }


    @RequestMapping(value = "quicksms", method = RequestMethod.POST)
    public String sendQuickSms(Model model, @RequestParam("message") String message, @RequestParam("recipient-list") String recipients) {

        String[] recipientList = recipients.split("\\n");

        SendMessageResult messageResult = smsService.sendQuickSms(message, recipientList);
        System.out.println(messageResult);


        model.addAttribute("messageresult", messageResult);
        addCurrentUserToModel(model);
        return "appcenter";
    }

    @RequestMapping(value = "bulksms", method = RequestMethod.GET)
    public String bulkSMSForm(Model model) {
        model.addAttribute("fieldlist", getFieldList());
        addGroupListToModel(model);
        addCurrentUserToModel(model);
        return "bulksms";
    }

    /**
     * Private methods
     */

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }


    private void addGroupListToModel(Model model) {
        User currentUser = getCurrentUser();
        List<Group> groupsForAccount = contactsService.getGroupsForAccount(currentUser.getAccountKey());
        List<GroupResource> groupResources = new ArrayList<>();

        for (Group group :groupsForAccount){
            GroupResource resource = new GroupResource();
            resource.setName(group.getName());
            resource.setGroupid(group.getGroupidString());
            groupResources.add(resource);
        }

        model.addAttribute("grouplist", groupResources);
    }

    private void addCurrentUserToModel(Model model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);
        double balance = user.getAccount().getAccountBalance().getBalance();
        model.addAttribute("balance", balance);
    }

    private List<String> getFieldList() {
        List<String> recipientFields = new ArrayList<>();
        Field[] allFields = Recipient.class.getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                String capitalizedField = WordUtils.capitalize(field.getName());
                recipientFields.add(capitalizedField);
            }
        }

        log.info("Fields for Recipient: {}", recipientFields);
        return recipientFields;
    }


}
