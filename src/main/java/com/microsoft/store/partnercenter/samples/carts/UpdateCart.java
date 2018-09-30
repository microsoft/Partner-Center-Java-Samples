// -----------------------------------------------------------------------
// <copyright file="UpdateCart .java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.carts;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.carts.Cart;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that updates a cart for a customer.
 */
public class UpdateCart extends BasePartnerScenario 
{
    public UpdateCart  (IScenarioContext context) 
    {
		super("Update a Cart", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId( "Enter the ID of the customer making the purchase" );
        String cartId = this.obtainCartId("Enter the ID of cart to be updated");
        int quantityChange = Integer.parseInt(this.obtainQuantity("Enter the amount the quantity has to be changed"));

        Cart existingCart = partnerOperations.getCustomers().byId(customerId).getCarts().byId(cartId).get();
        this.getContext().getConsoleHelper().writeObject( existingCart, "Cart to be updated" );

        this.getContext().getConsoleHelper().startProgress("Updating cart");
        
        existingCart.getLineItems().get(0).setQuantity(
            existingCart.getLineItems().get(0).getQuantity() + quantityChange
        );
        
        Cart updatedCart = partnerOperations.getCustomers().byId(customerId).getCarts().byId(cartId).put(existingCart);

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject(updatedCart, "Updated cart" );
	}
}