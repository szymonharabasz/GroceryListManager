package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.UserService;
import com.szymonharabasz.grocerylistmanager.validation.Alphanumeric;
import com.szymonharabasz.grocerylistmanager.validation.Password;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import java.io.IOException;

@Named
@RequestScoped
public class RegisterBacking {

    @Inject
    private UserService userService;

    @NotBlank
    @Alphanumeric
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    @Password
    private String password;
    @NotBlank
    @Password
    private String repeatPassword;
    @NotBlank
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void register() {
        userService.save(new User(Utils.generateID(), username, password, email));
    }
}
