package com.dev.cinema.service.impl;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private static UserService userService;

    @Override
    public User login(String email, String password)
            throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return user;
        }
        throw new AuthenticationException("User hasn't been authenticated " + email);
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
