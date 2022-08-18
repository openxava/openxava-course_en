package com.yourcompany.invoicing.model;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity	 @Getter @Setter
@View( extendsView="super.DEFAULT", // The default view
members="invoice { invoice } "
)
@View( name="NoCustomerNoInvoice", // A view named NoCustomerNoInvoice
members=                       // that does not include customer and invoice.
    "year, number, date;" +    // Ideal to be used from Invoice
    "details;" +
    "remarks"
)
public class Order extends CommercialDocument{

    @ManyToOne
    @ReferenceView("NoCustomerNoOrders") // This view is used to display invoice
    private Invoice invoice;
	
}
