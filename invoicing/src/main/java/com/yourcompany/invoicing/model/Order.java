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
//Validation alternative with validator,  if use this, you have to change in i18n for
//order_must_be_delivered=Order {0}/{1} must be delivered in order to be added to an Invoice
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
    
    //if use Validation alternative with JPA callback method
    //have to comment @PrePersist @PreUpdate 
    @PrePersist @PreUpdate 
    private void recalculateDeliveryDays() {
        setDeliveryDays(getEstimatedDeliveryDays());
    }
    
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    boolean delivered;
    
//   //Validation alternative with JPA callback method
//    @PrePersist @PreUpdate // Just before creating or updating
//    private void validate()  throws Exception {
//        if (invoice != null && !isDelivered()) { // The validation logic
//            // The validation exception from Bean Validation
//            throw new javax.validation.ValidationException(
//                XavaResources.getString(
//                    "order_must_be_delivered",
//                    getYear(),
//                    getNumber()
//                )
//            );
//        }
//    }
    
//   //Validation alternative with setter
//    public void setInvoice(Invoice invoice) {
//        if (invoice != null && !isDelivered()) { // The validation logic
//            // The validation exception from Bean Validation
//            throw new javax.validation.ValidationException(
//                XavaResources.getString(
//                    "order_must_be_delivered",
//                    getYear(),
//                    getNumber()
//                )
//            );
//        }
//        this.invoice = invoice; // The regular setter assignment
//    }
    
//   //Validation alternative with Bean Validation, if use this, you have to change in i18n for
//   //order_must_be_delivered=Order {year}/{number} must be delivered in order to be added to an Invoice
//    @AssertTrue( // Before saving it asserts if this method returns true, if not it throws an exception
//    	    message="order_must_be_delivered" // Error message in case false
//    	)
//    	private boolean isDeliveredToBeInInvoice() {
//    	    return invoice == null || isDelivered(); // The validation logic
//    	}
    
}
