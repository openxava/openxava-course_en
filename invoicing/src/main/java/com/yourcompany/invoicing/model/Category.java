package com.yourcompany.invoicing.model;
 
import javax.persistence.*;

import org.hibernate.validator.constraints.*;
import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Category extends Identifiable{
 
    @Column(length=50)
    String description; 
 
 
    @URL
    @Column(length=500)
    @ReadOnly
    String desc;
    
}