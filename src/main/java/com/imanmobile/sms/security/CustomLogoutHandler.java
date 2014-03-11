package com.imanmobile.sms.security;

import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jome on 2014/03/05.
 */

@Component
public class CustomLogoutHandler extends SecurityContextLogoutHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    Configuration configuration;

    private boolean invalidateHttpSession = true;
    private boolean clearAuthentication = true;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            //Seen cases where the client returns an unauthorized exception.
            //We want to deal with it here and carry through the session invalidation.
            SMSClient client = new SMSClient(configuration);
            client.getCustomerProfileClient().logout();
            configuration.setAuthentication(null);
        } catch (RequestException re) {
            logger.error("Infobip exception: {}", re.getMessage());
        } finally {
            Assert.notNull(request, "HttpServletRequest required");
            if (invalidateHttpSession) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    logger.debug("Invalidating session: " + session.getId());
                    logger.info("Invalidating session " + session.getId());
                    session.invalidate();
                }
            }

            if (clearAuthentication) {
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(null);
            }

            SecurityContextHolder.clearContext();

        }

    }

}
