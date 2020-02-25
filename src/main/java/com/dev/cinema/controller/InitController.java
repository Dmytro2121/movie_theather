package com.dev.cinema.controller;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public InitController(UserService userService, RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void setRoles() {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleService.add(userRole);

        User user = new User();
        user.setEmail("a@a.ua");
        user.setPassword(passwordEncoder.encode("1234"));
        user.addRole(adminRole);
        userService.add(user);
    }
}
