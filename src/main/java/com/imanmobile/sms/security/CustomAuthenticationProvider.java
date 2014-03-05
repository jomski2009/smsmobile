package com.imanmobile.sms.security;

import com.imanmobile.sms.domain.Account;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.exception.RequestException;
import com.imanmobile.sms.oneapi.model.Authentication;
import com.imanmobile.sms.oneapi.model.common.AccountBalance;
import com.imanmobile.sms.oneapi.model.common.CustomerProfile;
import com.imanmobile.sms.oneapi.model.common.LoginResponse;
import com.imanmobile.sms.services.AccountsService;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    AccountsService accountsService;

    @Autowired
    SmsService smsService;

    @Autowired
    Configuration configuration;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        System.out.println("Authenticating: " + username);
        String password = (String) authentication.getCredentials();
        logger.info("User password {}", password);

        if (!StringUtils.hasText(password)) {
            logger.warn("Username {}: no password provided", username);
            throw new BadCredentialsException("Please enter password");
        }
        //Check with infobip for valid login credentials...
        LoginResponse loginResponse;

        try {
            Authentication auth = new Authentication(username, password);
            configuration.setAuthentication(auth);
            //Configuration configuration = new Configuration(username, password);
            SMSClient client = new SMSClient(configuration);
            loginResponse = client.getCustomerProfileClient().login();

//            loginResponse = profileClient.login(username, password);
            //loginResponse = smsService.login(username, password);
            logger.info("Login Response: {}", loginResponse);

            if (!loginResponse.isVerified()) {
                logger.warn("User details not verified by Infobip");
                throw new BadCredentialsException("Invalid login");
            } else {
                com.imanmobile.sms.domain.User person = userService.findByUsername(username);
                CustomerProfile customerProfile = client.getCustomerProfileClient().getCustomerProfile();
                Account account = client.getCustomerProfileClient().getCustomerAccount();
                AccountBalance balance = client.getCustomerProfileClient().getAccountBalance();
                logger.info("Customer account: {}", account);
                logger.info("Customer account balance: {}", balance);

                account.setAccountBalance(balance);

                if (person == null) {
                    //Must be a new user... create the account here.
                    person = new com.imanmobile.sms.domain.User();
                    BeanUtils.copyProperties(customerProfile, person);
                    userService.createUser(person);


                    //Check if the account already exists (We may just be creating additional users.
                    Account acct1 = accountsService.getAccountForKey(account.getKey());
                    if (acct1 != null) {
                        person.setAccount(acct1);
                        userService.save(person);
                    } else {
                        accountsService.save(account);
                        person.setAccount(account);
                        userService.save(person);
                    }
                }

                //Find the user


                //Pull data from infobip.
                logger.info("Current customer profile: {}", customerProfile);


//                if (!passwordEncoder.matches(password, person.getPassword())) {
//                    logger.warn("Username {} password {}: invalid password", username, password);
//                    throw new BadCredentialsException("Invalid Login");
//                }

                if (!person.isEnabled()) {
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

                User user = new User(username, password, person.isEnabled(), // enabled
                        true, // account not expired
                        true, // credentials not expired
                        true, // account not locked
                        auths);
                System.out.println("Authorities for current user: " + user.getAuthorities());

                return user;

            }

        } catch (RequestException re) {

            logger.info("Caught error is : {}", re.getMessage());
            logger.warn("User details not verified by Infobip");
            re.printStackTrace();
            throw new BadCredentialsException("Invalid login");

        }
    }
}