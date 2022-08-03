package com.yourcompany.invoicing.model;
 
import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Product {
 
    @Id @Column(length=9)
    int number;
 
    @Column(length=50) @Required
    String description;
    
    @ManyToOne( // The reference is persisted as a database relationship
            fetch=FetchType.LAZY, // The reference is loaded on demand
            optional=true) // The reference can have no value
    @DescriptionsList // Thus the reference is displayed using a combo
    Category category; // A regular Java reference
 
}