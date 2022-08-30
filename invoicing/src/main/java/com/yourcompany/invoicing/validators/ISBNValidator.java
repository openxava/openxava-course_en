package com.yourcompany.invoicing.validators; 

import javax.validation.*;
import javax.ws.rs.client.*; // To use JAX-RS

import org.apache.commons.logging.*; // To use Log
import org.openxava.util.*;

import com.yourcompany.invoicing.annotations.*;
 
public class ISBNValidator
    implements ConstraintValidator<ISBN, Object> {
	
    private static Log log = LogFactory.getLog(ISBNValidator.class); // Instantiate 'log'
 
    private static org.apache.commons.validator.routines.ISBNValidator
        validator = 
            new org.apache.commons.validator.routines.ISBNValidator();
 
    public void initialize(ISBN isbn) {
 
    }
 
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Is.empty(value)) return true;
        if (!validator.isValid(value.toString())) return false;
        return isbnExists(value); // Here we do the REST call
    }
    
    private boolean isbnExists(Object isbn) {
        try {
            // Here we use JAX-RS to call a REST service
            String response = ClientBuilder.newClient()
                .target("http://openlibrary.org/") // The site
                .path("/api/books") // The path of the service
                .queryParam("jscmd", "data") // Parameters
                .queryParam("format", "json")
                .queryParam("bibkeys", "ISBN:" + isbn) // The ISBN is a parameter
                .request()
                .get(String.class); // A String with the JSON
            return !response.equals("{}"); // Is the JSON empty? Enough for our case.
        }
        catch (Exception ex) {
            log.warn("Impossible to connect to openlibrary.org " +
                "to validate the ISBN. Validation fails", ex);
            return false; // If there are errors we assume that validation fails
        }
    }
    
}