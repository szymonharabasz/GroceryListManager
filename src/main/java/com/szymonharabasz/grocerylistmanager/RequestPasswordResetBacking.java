package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.ExpirablePayload;
import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.UserService;
import com.szymonharabasz.grocerylistmanager.service.UserTokenWrapper;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Resource;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Named
@RequestScoped
public class RequestPasswordResetBacking {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    UserService userService;

    @Inject
    HashingService hashingService;

    @Inject
    private Event<UserTokenWrapper> event;

    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void request() {
        userService.findByEmail(email).ifPresent(user ->
            hashingService.findSaltByUserId(user.getId()).ifPresent(salt -> {
                String passwordResetToken = RandomStringUtils.randomAlphanumeric(32);
                String passwordResetTokenHash = HashingService.createHash(passwordResetToken, salt.getSalt());
                Date expiresAt = Date.from(Instant.now().plus(Duration.ofMinutes(30)));
                user.setPasswordResetTokenHash(new ExpirablePayload(passwordResetTokenHash, expiresAt));
                userService.save(user);
                event.fireAsync(new UserTokenWrapper(user, passwordResetToken));
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() +
                            "/message.xhtml?type=password-reset-requested");
                } catch (IOException e) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occured when redirecting to the confirmation page.", null));

                }
            })
        );
    }

}
