package com.yourcompany.invoicing.model;

import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity	 @Getter @Setter
@View(extendsView="super.DEFAULT", 
members=
    "estimatedDeliveryDays," + // ADD THIS LINE
    "invoice { invoice }"
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
	
    @Depends("date")
    public int getEstimatedDeliveryDays() {
        if (getDate().getDayOfYear() < 15) {
            return 20 - getDate().getDayOfYear(); 
        }
        if (getDate().getDayOfWeek() == DayOfWeek.SUNDAY) return 2;
        if (getDate().getDayOfWeek() == DayOfWeek.SATURDAY) return 3;
        return 1;
    }
    
    @Column(columnDefinition="INTEGER DEFAULT 1")
    int deliveryDays;
    
    @PrePersist @PreUpdate 
    private void recalculateDeliveryDays() {
        setDeliveryDays(getEstimatedDeliveryDays());
    }
}
