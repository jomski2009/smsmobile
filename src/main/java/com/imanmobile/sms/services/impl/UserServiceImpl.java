package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.domain.User;
import com.imanmobile.sms.services.UserService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by jome on 2014/02/28.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Datastore ds;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public String createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(1); //USER
        Key<User> userKey = ds.save(user);

        return userKey.getId().toString();
    }

    @Override
    public User findByUsername(String username) {
        User user = ds.createQuery(User.class).field("_id").equal(username).get();
        return user;
    }
}
