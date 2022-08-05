package com.yourcompany.invoicing.model;
 
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.yourcompany.invoicing.calculators.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Invoice {

    @Id
    @GeneratedValue(generator="system-uuid")
    @Hidden
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(length=32)
    String oid;

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
    Customer customer;
    
    @ElementCollection
    @ListProperties("product.number, product.description, quantity")
    Collection<Detail> details;
    
    @TextArea
    String remarks;
 
}