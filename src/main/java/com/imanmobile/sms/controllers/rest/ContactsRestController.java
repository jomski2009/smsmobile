package com.imanmobile.sms.controllers.rest;

import com.imanmobile.sms.domain.Recipient;
import com.imanmobile.sms.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, Object>> getGroupMembers(@PathVariable("groupid") String groupid) {
        List<Recipient> recipients = contactsService.getGroupRecipients(groupid);
        Map<String, Object> results = new HashMap<>();
        results.put("recipients", recipients);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
