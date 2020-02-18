package com.dev.cinema.controller;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.model.dto.UserRequestDto;
import com.dev.cinema.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody UserRequestDto userDto) {
        authenticationService.register(userDto.getEmail(), userDto.getPassword());
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody UserRequestDto userDto) {
        try {
            authenticationService.login(userDto.getEmail(), userDto.getPassword());
            return "Success";
        } catch (AuthenticationException e) {
            return "Wrong login, password or both";
        }
    }
}
