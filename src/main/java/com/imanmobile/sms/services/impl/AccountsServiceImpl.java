package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.domain.Account;
import com.imanmobile.sms.services.AccountsService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jome on 2014/03/05.
 */

@Service
public class AccountsServiceImpl implements AccountsService {
    @Autowired
    Datastore ds;

    public Account getAccountForKey(String accountKey) {
        Account account = ds.createQuery(Account.class).field("_id").equal(accountKey).get();

        if (account != null) {
            return account;
        } else {
            return null;
        }
    }

    @Override
    public String save(Account account) {
        Key<Account> accountKey = ds.save(account);
        return accountKey.getId().toString();
    }
}
