// -----------------------------------------------------------------------
// <copyright file="GetCustomerSkus.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the skus related to a product that apply to a customer.
 */
public class GetCustomerSkus extends BasePartnerScenario 
{
    public GetCustomerSkus (IScenarioContext context) 
    {
		super("Get SKUs for a customer", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the corresponding customer");
        String productId = this.obtainProductId("Enter the ID of the corresponding product");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting skus from product {0} for customer {1}", productId, customerId));
        
        ResourceCollection<Sku> skus = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(skus, "Skus for customer");
	}
}