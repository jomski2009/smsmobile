package com.imanmobile.sms.services;

import com.imanmobile.sms.domain.User;

/**
 * Created by jome on 2014/02/28.
 */
public interface UserService {
    String createUser(User user);

    User findByUsername(String username);

    void save(User person);
}
