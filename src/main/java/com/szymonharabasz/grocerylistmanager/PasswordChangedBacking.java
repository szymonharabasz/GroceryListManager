package com.szymonharabasz.grocerylistmanager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PasswordChangedBacking {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    public void load() {
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created",
                        "Your password has been successfuly changed."));
    }
}
