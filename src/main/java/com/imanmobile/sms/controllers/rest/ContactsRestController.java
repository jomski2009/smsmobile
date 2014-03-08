package com.imanmobile.sms.controllers.rest;

import com.imanmobile.sms.domain.Group;
import com.imanmobile.sms.domain.Recipient;
import com.imanmobile.sms.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jome on 2014/03/03.
 */

@RestController
@RequestMapping(value = "api/v1/contacts")
public class ContactsRestController {

    @Autowired
    ContactsService contactsService;

    @RequestMapping("groups/{groupid}/members")
    public HttpEntity getGroupMembers(@PathVariable("groupid") String groupid) {
        List<Recipient> recipients = contactsService.getGroupRecipients(groupid);
        Map<String, Object> results = new HashMap<>();
        results.put("recipients", recipients);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

//    @RequestMapping("groups/add")
//    public HttpEntity createGroup(@RequestParam("name") String name, @RequestParam("description") String description){
//        Group group = contactsService.createGroup(name, description);
//
//        return new ResponseEntity<>(group, HttpStatus.OK);
//    }

    @RequestMapping(value = "groups/add", consumes = "application/json", method = RequestMethod.POST)
    public HttpEntity createGroup(@Valid @RequestBody Group group, BindingResult bindingResult) {
//        Map<String, Object> errors = new HashMap<>();
//        if(group.getName().isEmpty()){
//            errors.put("error", "The name field is required");
//            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
//        }
//        if(!group.getName().isEmpty() && group.getName().length() < 6 || group.getName().length()> 30){
//            errors.put("error", "The group name must be from 6 to 30 characters long ");
//            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
//        }
//        System.out.println(group);
//        Group newgroup = contactsService.createGroup(group.getName(), group.getDescription());
//        return new ResponseEntity<>(newgroup, HttpStatus.OK);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            Group newgroup = contactsService.createGroup(group.getName(), group.getDescription());
            return new ResponseEntity<>(newgroup, HttpStatus.OK);
        }

    }

}
