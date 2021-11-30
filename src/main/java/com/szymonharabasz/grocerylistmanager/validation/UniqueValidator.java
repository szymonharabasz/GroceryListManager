package com.szymonharabasz.grocerylistmanager.validation;

import com.szymonharabasz.grocerylistmanager.service.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Inject
    UserService userService;

    private String fieldName;

    @Override
    public void initialize(final Unique unique) {
        fieldName = unique.name();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.err.println("!!!! UNIQUE VALIDATOR HAS BEEN CALLED !!!!");
        if (userService.findBy(fieldName, s).isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
