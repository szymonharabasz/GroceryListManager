package com.szymonharabasz.grocerylistmanager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.IIOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBacking {
    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    private String username;
    private String password;

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

    public void handleLogin() throws IOException {
        System.out.println("Logged in " + username + ", " + password);
        UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential(username, password);
        AuthenticationParameters authenticationParameters = AuthenticationParameters.withParams().credential(usernamePasswordCredential);
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        AuthenticationStatus authenticationStatus = securityContext.authenticate(request, response, authenticationParameters);
        System.err.println("Authentication status: " + authenticationStatus);
        switch (authenticationStatus) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Wrong user name or password", null));
                break;
            case SUCCESS:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeeded", null));
                externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
                break;
            case NOT_DONE:
        }
    }
}
