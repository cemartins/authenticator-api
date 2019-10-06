package com.assessment.authentication.jwt;

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

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private Map<Integer, User> userRegistry = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> apiUser = userRegistry.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();

        return apiUser.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User loadUserByAccountNumber(Integer account) {
        return userRegistry.get(account);
    }

    public void storeUser(Integer account, String username, String password) {
        final String encodedPassword = bcryptEncoder.encode(password);
        final User user = new User(username, encodedPassword, new ArrayList<>());
        userRegistry.put(account, user);
    }

}
