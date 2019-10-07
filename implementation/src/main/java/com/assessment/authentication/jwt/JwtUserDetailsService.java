package com.assessment.authentication.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private Map<String, User> userRegistry = new HashMap<>();
    private Map<String, Integer> consecutiveUserFailures = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> apiUser = userRegistry.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();

        return apiUser.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User loadUserByAccountNumber(Integer account) {
        return userRegistry.get(account);
    }

    /**
     * Stores a new user in the memory map
     * @param iban iban of the user
     * @param username user name
     * @param password password
     */
    public void storeUser(String iban, String username, String password) {
        final String encodedPassword = bcryptEncoder.encode(password);
        final User user = new User(username, encodedPassword, new ArrayList<>());
        userRegistry.put(iban, user);
    }

    /**
     * A user failed to authenticate
     * @param username
     */
    public void reportLoginFailure(String username) {

        LOG.debug("Authentication failed for " + username);
        Integer failures = consecutiveUserFailures.get(username);
        if(failures == null) {
            failures = 0;
        }
        else {
            failures = failures + 1;
        }
        consecutiveUserFailures.put(username, failures);
        if(failures == 3) {
            final Optional<User> apiUser = userRegistry.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();
            LOG.info("Authentication failed max times for " + username + ". User is blocked");
        }
    }

    /**
     * A user authenticated successfuly
     * @param username
     */
    public void reportLoginSuccess(String username) {
        LOG.info("Authentication successful for " + username);
        consecutiveUserFailures.put(username, 0);
    }

}
