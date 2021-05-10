package com.exercise.studentmanageexercise.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AgeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE,
    ElementType.PARAMETER, ElementType.TYPE, ElementType.PACKAGE})
@Retention(RUNTIME)
public @interface AgeCheck {
    String message() default "Age must be in 18 - 80";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}