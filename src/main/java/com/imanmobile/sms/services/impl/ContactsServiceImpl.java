package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.domain.Group;
import com.imanmobile.sms.domain.Recipient;
import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.services.ContactsService;
import com.imanmobile.sms.services.UserService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jome on 2014/03/01.
 */

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    Datastore ds;

    @Autowired
    UserService userService;

    @Autowired
    Configuration configuration;

    @Override
    public Group createGroup(String username, Group group) {
        return null;
    }

    @Override
    public String createGroup(Group group) {
        return ds.save(group).getId().toString();
    }

    @Override
    public String createGroup(String name, String description, String username) {
        return null;
    }

    @Override
    public Group createGroup(String name, String description) {

        String username = configuration.getAuthentication().getUsername();
        User user = ds.createQuery(User.class).field("_id").equal(username).get();
        String accountKey = user.getAccountKey();
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setAccountKey(accountKey);
        group.setCreationdate(new Date().getTime());

        ds.save(group);
        return group;
    }

    @Override
    public void addContactsToGroup(String groupid, List<String> contacts) {
        Group group = ds.find(Group.class, "_id", new ObjectId(groupid)).get();

        for (String contact : contacts) {
            String[] splitContact = contact.split(",");
            Recipient recipient = new Recipient();
            recipient.setCellnumber(splitContact[0].trim());

            if (splitContact.length >= 2 && splitContact[1] != null && splitContact[1].trim().length() > 0) {
                recipient.setFirstname(splitContact[1]);
            }

            if (splitContact.length >= 3 && splitContact[2] != null && splitContact[2].trim().length() > 0) {
                recipient.setLastname(splitContact[2]);
            }


            group.getRecipients().add(recipient);
        }

        ds.save(group);
    }

    @Override
    public List<Recipient> getGroupRecipients(String groupid) {
        Group group = ds.find(Group.class, "_id", new ObjectId(groupid)).get();
        List<Recipient> recipients = group.getRecipients();
        return recipients;
    }

    @Override
    public List<Group> getGroupsForAccount(String accountKey) {
        List<Group> groups = ds.createQuery(Group.class).field("accountKey").equal(accountKey).asList();
        return groups;
    }
}
