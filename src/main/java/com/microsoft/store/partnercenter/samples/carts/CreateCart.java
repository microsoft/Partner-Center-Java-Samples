// -----------------------------------------------------------------------
// <copyright file="CreateCart.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.carts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.carts.Cart;
import com.microsoft.store.partnercenter.models.carts.CartLineItem;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that creates a new cart for a customer.
 */
public class CreateCart  extends BasePartnerScenario 
{
    public CreateCart (IScenarioContext context) 
    {
		super("Create a cart", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer making the purchase" );
        String catalogItemId = this.obtainCatalogItemId("Enter the catalog Item Id");
        String productId = catalogItemId.split(":")[0];
        String skuId = catalogItemId.split(":")[1];
        String scope = "";
        String subscriptionId = "";
        String duration = "";

        Sku sku = partnerOperations.getProducts().byCountry("US").byId(productId).getSkus().byId(skuId).get();

        if (sku.getProvisioningVariables() != null)
        {
            scope = this.obtainScope("Enter the Scope for the Provisioning status");
            subscriptionId = this.obtainAzureSubscriptionId("Enter the Subscription Id");
            duration = sku.getDynamicAttributes().get("duration");
        }

        CartLineItem lineItem = new CartLineItem();

        lineItem.setBillingCycle(sku.getSupportedBillingCycles().get(0));
        lineItem.setCatalogItemId(catalogItemId);
        lineItem.setFriendlyName("Myofferpurchase");
        lineItem.setQuantity(1);

        if(sku.getProvisioningVariables() == null)
        {
            lineItem.setProvisioningContext(null);
        }
        else 
        {
            Map<String, String> provisioningContext = new HashMap<String,String>();

            provisioningContext.put("duration", duration);
            provisioningContext.put("scope", scope); 
            provisioningContext.put("subscriptionId", subscriptionId);

            lineItem.setProvisioningContext(provisioningContext);
        }

        List<CartLineItem> lineItemList = new ArrayList<CartLineItem>();
        lineItemList.add(lineItem);

        Cart cart = new Cart();
        cart.setLineItems(lineItemList);

        this.getContext().getConsoleHelper().writeObject( cart, "Cart to be created" );
        this.getContext().getConsoleHelper().startProgress( "Creating cart" );
        
        Cart cartCreated = partnerOperations.getCustomers().byId(customerId).getCarts().create(cart);

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( cartCreated, "Created cart" );
	}
}