package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.Salt;
import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ResetPasswordBacking {

    private String newPassword;
    private String token;
    private User user;
    private Salt salt;

    @Inject
    private UserService userService;

    @Inject
    private HashingService hashingService;

    public void load() {

    }

    public void resetPassword() {
        user.setPasswordResetTokenHash(null);
        hashingService.findSaltByUserId(user.getId()).ifPresent(salt -> {

        });
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
