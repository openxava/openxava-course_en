package com.yourcompany.invoicing.model;
 
import java.math.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Category {
 
    @Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    String oid;
 
    @Column(length=50)
    String description;
    
    @Money // Instead of @Stereotype("MONEY") 
    BigDecimal price; 
     
    @Files // Instead of @Stereotype("IMAGES_GALLERY") 
    @Column(length=32)
    String photos;
     
    @TextArea // Instead of @Stereotype("MEMO") 
    String remarks;
 
}