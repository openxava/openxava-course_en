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
 
}