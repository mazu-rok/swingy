package com.amazurok.swingy.anotation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidateTypeConstraint implements ConstraintValidator<ValidateType, String> {
    private List<String> values;

    @Override
    public void initialize(ValidateType constraintAnnotation)
    {
        values = new ArrayList<>();
        Collections.addAll(values, constraintAnnotation.types());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return values.contains(value);
    }
}
