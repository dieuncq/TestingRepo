package com.exercise.studentmanageexercise.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Date;

@Component
public class AgeConstraintValidator implements ConstraintValidator <AgeCheck, LocalDate>{

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
//        long ageInMillis = new Date().getTime() - value.getTime();
//        Date ageDate = new Date(ageInMillis);
//
//        @Deprecated
//        int age = ageDate.getYear();
//        System.out.println(age);

        int age = LocalDate.now().getYear() - value.getYear();
        System.out.println(age);

        if (age >= 18 && age <= 80) {
            return true;
        } else {
            return false;
        }
    }
}
