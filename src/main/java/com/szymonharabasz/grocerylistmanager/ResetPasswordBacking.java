package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.Salt;
import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Named
@SessionScoped
public class ResetPasswordBacking implements Serializable {

    private String userName;
    private String newPassword;
    private String token;
    private User user;
    private Salt salt;

    @Inject
    private UserService userService;

    @Inject
    private HashingService hashingService;

    @Inject
    private ExternalContext externalContext;

    public void load() {
        Optional<User> maybeUser = userService.findByName(userName);
        maybeUser.ifPresent(usr -> {
            Optional<Salt> maybeSalt = hashingService.findSaltByUserId(usr.getId());
            maybeSalt.ifPresent(slt -> {
                user = usr;
                salt = slt;
                String tokenHash = HashingService.createHash(token, salt);
                if (!Objects.equals(tokenHash, user.getPasswordResetTokenHash())) {
                    showError();
                }
            });
            System.out.println("USER IS " + user + " AND SALT IS " + salt);
            if (!maybeSalt.isPresent()) {
                showError();
            }
        });
        if (!maybeUser.isPresent()) {
            showError();
        }
    }

    public void resetPassword() {
        String newPasswordHash = HashingService.createHash(newPassword, salt);
        user.setPasswordHash(newPasswordHash);
        user.setPasswordResetTokenHash(null);
        userService.save(user);
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/message.xhtml?type=password-changed");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occured when redirecting to the confirmation page.", null));
        }
    }

    private void showError() {
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/message.xhtml?type=wrong-token");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occured when redirecting to the message page.", null));
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
