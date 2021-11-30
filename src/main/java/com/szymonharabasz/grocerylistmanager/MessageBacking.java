package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.ConfirmationMailService;
import com.szymonharabasz.grocerylistmanager.service.UserService;
import com.szymonharabasz.grocerylistmanager.validation.Alphanumeric;
import com.szymonharabasz.grocerylistmanager.validation.Password;
import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;

@Named
@RequestScoped
public class MessageBacking {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    private String type;

    private String title;

    private String header;

    public void load() {
        if (type == "email-sent") {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created",
                            "An e-mail has been sent to the address you provided in the registration. " +
                                    "Check your mailbox and click the confirmation link to activate your account"));
            title = "Confirma e-mail address";
            header = "E-mail address confirmation";
        } else if (type == "password-changed") {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created",
                            "Your password has been successfuly changed."));
            title = "Password changed";
            header = "Password changed";
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
