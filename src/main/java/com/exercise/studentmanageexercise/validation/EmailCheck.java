package com.exercise.studentmanageexercise.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailConstaintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE,
        ElementType.PARAMETER, ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailCheck {
    String message() default "Email has invalid format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
