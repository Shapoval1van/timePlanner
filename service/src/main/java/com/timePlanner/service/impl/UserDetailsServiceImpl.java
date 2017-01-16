package com.timePlanner.service.impl;

import com.timePlanner.dto.User;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByEmail(s);
            List auths = new ArrayList();
            auths.add(new SimpleGrantedAuthority(user.getRole().toString()));
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), auths);
        } catch (EmptyResultException e) {
            LOGGER.info("User with email:" + s + "doesn't exist");
            throw new UsernameNotFoundException("User with email:" + s + "doesn't exist");
        }
    }
}
