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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @RequestMapping(value = "/contacts/groups/{groupid}/addcontacts", method = RequestMethod.POST)
    public String addContactsToGroup(@RequestParam("file") MultipartFile csvfile, @PathVariable("groupid") String groupid, Model model) {

        List<String> rows = new ArrayList<String>();
        try {
            log.info("Starting csv processing...");

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

        return "redirect:/contacts";
    }

    @RequestMapping(value = "/contacts/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(
            @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("test" + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + "test" + " into " + "test" + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + "test" + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + "test" + " because the file was empty.";
        }
    }

}
