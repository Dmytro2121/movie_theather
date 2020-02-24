package com.dev.cinema.controller;

import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private UserService userService;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    public InitController(UserService userService,
                          RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void setRoles() {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleDao.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleDao.add(userRole);

        User user = new User();
        user.setEmail("a@a.ua");
        user.setPassword(passwordEncoder.encode("1234"));
        user.addRole(adminRole);
        userService.add(user);
    }
}
