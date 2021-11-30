package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.Salt;
import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.ConfirmationMailService;
import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.UserService;
import com.szymonharabasz.grocerylistmanager.validation.Alphanumeric;
import com.szymonharabasz.grocerylistmanager.validation.Password;
import com.szymonharabasz.grocerylistmanager.validation.Unique;
import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.*;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

@Named
@RequestScoped
public class RegisterBacking {

    @Inject
    private UserService userService;

    @Inject
    private HashingService hashingService;

    @Inject
    private ConfirmationMailService confirmationMailService;

    @Inject
    private Event<User> userRegistrationEvent;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

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
    @Unique(name = "email", message = "this e-mail address has been already regustered")
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
        Salt salt = new Salt(Utils.generateID(), HashingService.createSalt());
        hashingService.save(salt);
        User user = new User(salt.getUserId(), username, HashingService.createHash(password, salt), email);
        user.setConfirmationToken(RandomStringUtils.randomAlphanumeric(32));
        userService.save(user);
        userRegistrationEvent.fireAsync(user);
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created",
                        "An e-mail has been sent to the address you provided in the registration. " +
                                "Check your mailbox and click the confirmation link to activate your account.e"));
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/message.xhtml?type=email-sent");
        } catch (IOException e) {
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occured when redirecting to the confirmation page.", null));

        }
    }
}
