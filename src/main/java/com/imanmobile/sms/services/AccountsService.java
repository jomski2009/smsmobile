package com.imanmobile.sms.services;

import com.imanmobile.sms.domain.Account;

/**
 * Created by jome on 2014/03/05.
 */
public interface AccountsService {
    Account getAccountForKey(String accountKey);

    String save(Account account);
}
