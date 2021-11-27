package com.szymonharabasz.grocerylistmanager.domain;

import jakarta.nosql.mapping.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findByName(String name);
}
