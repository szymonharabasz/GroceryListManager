package com.szymonharabasz.grocerylistmanager.service;

import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.domain.UserRepository;
import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    @Database(DatabaseType.DOCUMENT)
    UserRepository repository;

    public void save(User user) {
        repository.save(user);
    }

    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<User> findByConfirmationToken(String token) { return repository.findByConfirmationToken(token); }
}
