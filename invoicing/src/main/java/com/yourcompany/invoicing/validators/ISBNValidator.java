package com.yourcompany.invoicing.validators; // In 'validators' package

import javax.validation.*;

import org.openxava.util.*;

import com.yourcompany.invoicing.annotations.*;
 
public class ISBNValidator implements ConstraintValidator<ISBN, Object> { // Must implement ConstraintValidator
 
    private static org.apache.commons.validator.routines.ISBNValidator
        validator = // From 'Commons Validator' framework
            new org.apache.commons.validator.routines.ISBNValidator();
 
    public void initialize(ISBN isbn) {
 
    }
 
    // Contains the validation logic
    public boolean isValid(Object value, ConstraintValidatorContext context) { 
        if (Is.empty(value)) return true;
        return validator.isValid(value.toString()); // Relies on 'Commons Validator'
    }
}