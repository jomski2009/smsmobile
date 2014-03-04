package com.imanmobile.sms.security;

import com.imanmobile.sms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/02/28.
 */
@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        System.out.println("Authenticating: " + username);
        String password = (String) authentication.getCredentials();
        if (!StringUtils.hasText(password)) {
            logger.warn("Username {}: no password provided", username);
            throw new BadCredentialsException("Please enter password");
        }

        com.imanmobile.sms.domain.User person = userService.findByUsername(username);
        if (person == null) {
            logger.warn("Username {} password {}: user not found", username, password);
            throw new UsernameNotFoundException("Invalid Login");
        }

        if (!passwordEncoder.matches(password, person.getPassword())) {
            logger.warn("Username {} password {}: invalid password", username, password);
            throw new BadCredentialsException("Invalid Login");
        }

//        if (!(PersonAccountStatus.STATUS_APPROVED.equals(person.getStatus()))) {
//        	logger.warn("Username {}: not approved", username);
//            throw new BadCredentialsException("User has not been approved");
//        }
        if (!person.isActive()) {
            logger.warn("Username {}: disabled", username);
            throw new BadCredentialsException("User disabled");
        }

        List<GrantedAuthority> auths = new ArrayList<>();
        int roleId = person.getRole();

        switch (roleId) {
            case 1:
                auths.add(new SimpleGrantedAuthority("USER"));
                break;
            case 2:
                auths.add(new SimpleGrantedAuthority("USER"));
                auths.add(new SimpleGrantedAuthority("ADMIN"));
                break;
            default:
                auths = AuthorityUtils.NO_AUTHORITIES;
        }

        User user = new User(username, password, person.isActive(), // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                auths);
        System.out.println("Authorities for current user: " + user.getAuthorities());

        return user;
    }
}