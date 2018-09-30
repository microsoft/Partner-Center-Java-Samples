// -----------------------------------------------------------------------
// <copyright file="GetCustomerProducts.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Product;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the products in a catalog view that apply to a costumer.
 */
public class GetCustomerProducts extends BasePartnerScenario 
{
    public GetCustomerProducts (IScenarioContext context) 
    {
		super("Get products for a customer", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the corresponding customer");
        String targetView =
                this.getContext().getConsoleHelper().readNonEmptyString( 
                    "Enter the target view to get its supported products",
                    "The target view can't be empty" );

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting products in catalog view {0} for customer {1}", targetView, customerId));
        
        ResourceCollection<Product> products  = partnerOperations.getCustomers().byId(customerId).getProducts().byTargetView(targetView).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(products, "Products for a customer");
	}
}