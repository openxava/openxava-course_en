package com.yourcompany.invoicing.model;
 
import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@MappedSuperclass @Getter @Setter
public class Deletable extends Identifiable {
 
    @Hidden
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    boolean deleted;
 
}