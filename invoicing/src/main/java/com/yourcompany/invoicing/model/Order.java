package com.yourcompany.invoicing.model;

import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity	 @Getter @Setter
@View(extendsView="super.DEFAULT", 
members=
	"estimatedDeliveryDays, delivered," + // Add delivered
    "invoice { invoice }"
)
@View( name="NoCustomerNoInvoice", // A view named NoCustomerNoInvoice
members=                       // that does not include customer and invoice.
    "year, number, date;" +    // Ideal to be used from Invoice
    "details;" +
    "remarks"
)
@EntityValidator(
	    value=com.yourcompany.invoicing.validators.DeliveredToBeInInvoiceValidator.class, // The class with the validation logic
	    properties= {
	        @PropertyValue(name="year"), // The content of these properties
	        @PropertyValue(name="number"), // is moved from the 'Order' entity
	        @PropertyValue(name="invoice"), // to the validator before
	        @PropertyValue(name="delivered") // executing the validation
	    }
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
    
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    boolean delivered;
    
}
