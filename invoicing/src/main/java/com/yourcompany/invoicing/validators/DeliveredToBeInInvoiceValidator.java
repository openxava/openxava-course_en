package com.yourcompany.invoicing.validators; // In 'validators' package

import org.openxava.util.*;
import org.openxava.validators.*;

import com.yourcompany.invoicing.model.*;

import lombok.*;
 
@Getter @Setter 
public class DeliveredToBeInInvoiceValidator
    implements IValidator { // Must implement IValidator
 
    int year; // Properties to be injected from Order
    int number;
    boolean delivered;
    Invoice invoice;
 
    public void validate(Messages errors) // The validation logic
        throws Exception
    {
        if (invoice == null) return;
        if (!delivered) {
            errors.add( // By adding messages to errors the validation will fail
                "order_must_be_delivered", // An id from i18n file
                year, number); // Arguments for the message
         }
    }

}