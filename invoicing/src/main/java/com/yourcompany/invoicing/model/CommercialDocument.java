package com.yourcompany.invoicing.model;
 
import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.yourcompany.invoicing.calculators.*;

import lombok.*;
 
@Entity @Getter @Setter
@View(members=
"year, number, date," + // The members for the header part in one line
"data {" + // A tab 'data' for the main data of the document
    "customer;" +
    "details;" +
    "remarks" +
"}"
)
abstract public class CommercialDocument extends Identifiable{

    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) // Current year
    int year;
 
    @Column(length=6)
    @DefaultValueCalculator(value=NextNumberForYearCalculator.class,
    properties=@PropertyValue(name="year") // To inject the value of year from Invoice to
                                               // the calculator before calling to calculate()
    )
    int number;
 
    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Current date
    LocalDate date;
 
    @ManyToOne(fetch=FetchType.LAZY, optional=false) // Customer is required
    @ReferenceView("Simple") // The view named 'Simple' is used to display this reference
    Customer customer;
    
    @ElementCollection
    @ListProperties(
            "product.number, product.description, quantity, pricePerUnit, " +
            "amount+[" + 
            	"commercialDocument.vatPercentage," +
            	"commercialDocument.vat," +
            	"commercialDocument.totalAmount" +
            "]" 
        )
    Collection<Detail> details;
    
    @TextArea
    String remarks;
 
    @Digits(integer=2, fraction=0) // To indicate its size
    BigDecimal vatPercentage;
       
    @ReadOnly
    @Money
    @Calculation("sum(details.amount) * vatPercentage / 100")
    BigDecimal vat;

    @ReadOnly
    @Money
    @Calculation("sum(details.amount) + vat")    
    BigDecimal totalAmount;    
}