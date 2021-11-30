package com.szymonharabasz.grocerylistmanager.domain;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity("User")
public class User {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String passwordHash;
    @Column
    private String email;
    @Column
    private List<String> listIds = new ArrayList<>();
    @Column
    private boolean confirmed;
    @Column
    private Date registered;
    @Column
    private String confirmationToken;
    @Column
    private String passwordResetTokenHash;

    public User() {}

    public User(String id, String name, String passwordHash, String email) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.confirmed = false;
        this.registered = new Date();
    }

    public void addListId(String id) {
        listIds.add(id);
    }

    public void removeListId(String id) {
        listIds.remove(id);
    }

    public boolean hasListId(String id) {
        return listIds.contains(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getPasswordResetTokenHash() {
        return passwordResetTokenHash;
    }

    public void setPasswordResetTokenHash(String passwordResetToken) {
        this.passwordResetTokenHash = passwordResetToken;
    }

}
