package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;

@Named
@RequestScoped
public class ConfirmationEmailBacking {
    @Inject
    private UserService userService;

    private String token;

    public void confirmEmail() {
        System.out.println("TOKEN " + token);
        Optional<User> user = userService.findByConfirmationToken(token);
        user.ifPresent(usr -> {
            usr.setConfirmed(true);
            usr.setConfirmationToken("");
            userService.save(usr);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                            "Your e-mail addeess has been successfully confirmed. You can now sign in to " +
                                    "your account"));
        });
        if (!user.isPresent()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "Your e-mail could not be confirmed. Try again, sign in to other account, " +
                                    "or try to register again."));
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
