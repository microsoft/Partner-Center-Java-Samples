// -----------------------------------------------------------------------
// <copyright file="CheckoutCart.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.carts;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.carts.Cart;
import com.microsoft.store.partnercenter.models.carts.CartCheckoutResult;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that checkout a cart for a customer.
 */
public class CheckoutCart extends BasePartnerScenario 
{
    public CheckoutCart (IScenarioContext context) 
    {
		super("Checkout a cart", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId( "Enter the ID of the customer making the purchase" );
        String cartId = this.obtainCartId("Enter the ID of cart to checkout");

        Cart existingCart = partnerOperations.getCustomers().byId(customerId).getCarts().byId(cartId).get();
        this.getContext().getConsoleHelper().writeObject( existingCart, "Cart to be checked out" );

        this.getContext().getConsoleHelper().startProgress("Checking out cart");
        
        CartCheckoutResult checkoutResult = partnerOperations.getCustomers().byId(customerId).getCarts().byId(cartId).checkout();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( checkoutResult, "Final Cart: " );
	}
}