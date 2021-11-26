package com.szymonharabasz.grocerylistmanager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Named
@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {
    private Set<String> userAdminRoleSet;

    @PostConstruct
    void init() {
        this.userAdminRoleSet = new HashSet<>(Arrays.asList("USER", "ADMIN"));
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        System.err.println("!!!!!!!! CALLING VALIDATE !!!!!!!!");
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        CredentialValidationResult credentialValidationResult;
        if (usernamePasswordCredential.compareTo("Carl", "pwd")) {
            credentialValidationResult = new CredentialValidationResult("Carl", userAdminRoleSet);
        } else {
            credentialValidationResult = CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        System.err.println("Credentials validation result " + credentialValidationResult.getStatus());
        return credentialValidationResult;
    }
}
