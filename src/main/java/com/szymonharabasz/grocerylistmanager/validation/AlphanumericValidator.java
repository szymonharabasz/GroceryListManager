package com.szymonharabasz.grocerylistmanager.validation;

import org.eclipse.jnosql.mapping.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Stream;

public class AlphanumericValidator implements ConstraintValidator<Alphanumeric, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (Character c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }
}
