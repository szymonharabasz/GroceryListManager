package com.szymonharabasz.grocerylistmanager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String smallLetters = "abcdefghijklmnopqrstuvwxyz";
    private static String digits = "1234567890";
    static String specialCharacters = " !\"#$%&\\'()*+,-./:;[\\\\]^_`{|}~'";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.err.println("!!!! PASSWORD VALIDATOR HAS BEEN CALLED !!!!");
        return s.length() >= 8 &&
                 Arrays.stream(capitalLetters.split("")).anyMatch(cs -> s.contains(cs)) &&
                 Arrays.stream(smallLetters.split("")).anyMatch(cs -> s.contains(cs)) &&
                 Arrays.stream(digits.split("")).anyMatch(cs -> s.contains(cs)) &&
                 Arrays.stream(specialCharacters.split("")).anyMatch(cs -> s.contains(cs));
    }
}
