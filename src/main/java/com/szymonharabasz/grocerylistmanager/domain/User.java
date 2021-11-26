package com.szymonharabasz.grocerylistmanager.domain;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import java.util.ArrayList;
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

    public User() {}

    public User(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
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
}
