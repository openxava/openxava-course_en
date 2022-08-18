package com.yourcompany.invoicing.model;
 
import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Embeddable @Getter @Setter
public class Detail {
 
    int quantity;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Product product;
    
    @Money
    @Depends("product.number, quantity")  // When the user changes product or quantity
    public BigDecimal getAmount() { // this property is recalculated and redisplayed
        if (product == null || product.getPrice() == null) return BigDecimal.ZERO;
        return new BigDecimal(quantity).multiply(product.getPrice()); 
    }
 
}