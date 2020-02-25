package com.dev.cinema.controller;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.UserRegistrationDto;
import com.dev.cinema.model.dto.UserRequestDto;
import com.dev.cinema.service.AuthenticationService;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")

    public User registerUser(@RequestBody @Valid UserRegistrationDto userDto) {
        if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
            return authenticationService.register(userDto.getEmail(), userDto.getPassword());
        } else {
            throw new DataProcessingException("Password should match");
        }
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody @Valid UserRequestDto userDto) {
        try {
            authenticationService.login(userDto.getEmail(), userDto.getPassword());
            return "Success";
        } catch (AuthenticationException e) {
            LOGGER.error(userDto.getEmail() + " hasn't been authenticated", e);
            return "Wrong login, password or both";
        }
    }
}
