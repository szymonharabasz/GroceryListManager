package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.UserService;

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

    @Inject
    private UserService userService;

    @Inject
    private HashingService hashingService;

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

    public void handleLogin() {
        System.out.println("Logged in " + username + ", " + password);
        userService.findByName(username).ifPresent(user ->
                hashingService.findSaltByUserId(user.getId()).ifPresent(salt -> {
                    String passwordHash = HashingService.createHash(password, salt);
                    UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential(username, passwordHash);
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
                            if (user.isConfirmed()) {
                                facesContext.addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeeded", null));
                                try {
                                    externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
                                } catch (IOException e) {
                                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "An error has occured when redirecting to the home page.", null));

                                }
                            } else {
                                facesContext.addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Your e-mail address is not confirmed. Check your mailbox " +
                                                        "to find a confirmation link.", null));
                            }
                            break;
                        case NOT_DONE:
                    }
                })
        );
    }
}
