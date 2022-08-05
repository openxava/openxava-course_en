package com.yourcompany.invoicing.calculators;
 
import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import lombok.*;
 
public class NextNumberForYearCalculator implements ICalculator { // A calculator must implement ICalculator

    @Getter @Setter // To be publicly accessible
    int year; // This value will be injected before calculating
 
    public Object calculate() throws Exception { // It does the calculation
        Query query = XPersistence.getManager() // A JPA query
        						  .createQuery("select max(i.number) from Invoice i where i.year = :year"); // The query returns
                                                                // the max invoice number of the indicated year
        query.setParameter("year", year); // We use the injected year as a parameter for the query
        Integer lastNumber = (Integer) query.getSingleResult();
        return lastNumber == null ? 1 : lastNumber + 1; // Returns the last invoice number
                                                        // of the year + 1 or 1 if there is no last number
    }
 
}