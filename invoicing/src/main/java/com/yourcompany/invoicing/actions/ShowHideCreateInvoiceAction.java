package com.yourcompany.invoicing.actions; // In the 'actions' package

import org.openxava.actions.*; // Needed to use OnChangePropertyAction,

public class ShowHideCreateInvoiceAction
    extends OnChangePropertyBaseAction  { // Needed for @OnChange actions (1)

    public void execute() throws Exception {
        if (isOrderCreated() && isDelivered() && !hasInvoice()) { // (2)
            addActions("Order.createInvoice");
        }
        else {
            removeActions("Order.createInvoice");
        }
    }
	
    private boolean isOrderCreated() {
        return getView().getValue("oid") != null; // We read the value from the view
    }
	
    private boolean isDelivered() {
        Boolean delivered = (Boolean)
            getView().getValue("delivered"); // We read the value from the view
        return delivered == null?false:delivered;
    }

    private boolean hasInvoice() {
        return getView().getValue("invoice.oid") != null; // We read the value from the view
    } 	
}