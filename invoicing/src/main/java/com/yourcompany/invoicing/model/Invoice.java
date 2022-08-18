package com.yourcompany.invoicing.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
@View( extendsView="super.DEFAULT", // The default view
members="orders { orders }"
)
@View( name="NoCustomerNoOrders", // A view named NoCustomerNoOrders
members=                      // that does not include customer and orders
    "year, number, date;" +   // Ideal to be used from Order
    "details;" +
    "remarks"
)
public class Invoice extends CommercialDocument{

    @OneToMany(mappedBy="invoice")
    @CollectionView("NoCustomerNoInvoice") // This view is used to display orders
    private Collection<Order> orders;
	
}
