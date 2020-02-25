package com.dev.cinema.model.dto;

import com.dev.cinema.security.EmailValidation;
import com.dev.cinema.security.PasswordMatch;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatch
public class UserRegistrationDto {
    @NotNull
    @EmailValidation
    private String email;
    @NotNull
    @Size(min = 3)
    private String password;
    @NotNull
    private String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
