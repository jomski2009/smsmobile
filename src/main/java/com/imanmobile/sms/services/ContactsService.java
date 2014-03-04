package com.imanmobile.sms.services;

import com.imanmobile.sms.domain.Group;
import com.imanmobile.sms.domain.Recipient;
import com.imanmobile.sms.domain.User;

import java.util.List;

/**
 * Created by jome on 2014/03/01.
 */
public interface ContactsService {
    Group createGroup(String username, Group group);
    String createGroup(Group group);
    String createGroup(String name, String description, String username);
    User createGroup(String name, String description);
    void addContactsToGroup(String groupid, List<String> contactsValues);
    List<Recipient> getGroupRecipients(String groupid);

}
