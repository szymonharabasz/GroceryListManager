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
    private String password;
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

    public User() {}

    public User(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
