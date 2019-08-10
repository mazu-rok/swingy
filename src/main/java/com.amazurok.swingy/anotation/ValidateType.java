package com.amazurok.swingy.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {ValidateTypeConstraint.class})
public @interface ValidateType {

    String[] types();

    String message() default "Invalid person type";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
