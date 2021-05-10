package com.exercise.studentmanageexercise.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailConstaintValidator implements ConstraintValidator<EmailCheck, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() < 10) {
            return false;
        }
        else if (!value.contains("@")) {
            return false;
        } else {
            return true;
        }
    }
}
