package com.assessment.authentication.security;

import com.assessment.authentication.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Listens to Authentication Failures
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent ev) {

        String username = ev.getAuthentication().getName();

        jwtUserDetailsService.reportLoginFailure(username);
    }
}
