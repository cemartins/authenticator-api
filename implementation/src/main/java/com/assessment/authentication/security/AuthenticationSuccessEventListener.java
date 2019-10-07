package com.assessment.authentication.security;

import com.assessment.authentication.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Listens to Authentication Success
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        String username = event.getAuthentication().getName();

        jwtUserDetailsService.reportLoginSuccess(username);

    }
}
