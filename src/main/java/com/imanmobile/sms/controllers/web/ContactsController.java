package com.imanmobile.sms.controllers.web;

import com.imanmobile.sms.domain.Group;
import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.services.ContactsService;
import com.imanmobile.sms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/03/01.
 */
@Controller
public class ContactsController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    ContactsService contactsService;


    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contactsView(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("usergroups", contactsService.getGroupsForAccount(user.getAccountKey()));
        return "contacts";
    }

    @RequestMapping(value = "/contacts/groups/add", method = RequestMethod.POST)
    public String addGroup(Model model, @RequestParam("name") String name, @RequestParam("description") String description) {

        Group group = contactsService.createGroup(name, description);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("usergroups", contactsService.getGroupsForAccount(user.getAccountKey()));
        return "contacts";
    }

    @RequestMapping(value = "/contacts/groups/{groupid}/addcontacts")
    public RedirectView addContactsToGroup(Model model, @PathVariable("groupid") String groupid, @RequestParam("csv-file") MultipartFile csvfile) {

        List<String> rows = new ArrayList<String>();
        try {
            System.out.println("Starting csv processing...");

            if (!csvfile.isEmpty()) {
                String row = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        csvfile.getInputStream()));
                while ((row = br.readLine()) != null) {
                    rows.add(row);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        contactsService.addContactsToGroup(groupid, rows);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);

        return new RedirectView("/contacts");
    }

}
