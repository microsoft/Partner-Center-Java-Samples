// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Availability;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the availabilities of a product's sku for a customer.
 */
public class GetCustomerAvailabilities extends BasePartnerScenario 
{
    public GetCustomerAvailabilities (IScenarioContext context) 
    {
		super("Get availabilities for customer", context);
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
        String skuId = this.obtainSkuId("Enter the ID of the corresponding sku");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting availabilities for product {0} and sku {1} for customer {2}", productId, skuId, customerId));
        
        ResourceCollection<Availability> skuAvailabilities = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().byId(skuId).getAvailabilities().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(skuAvailabilities, "Availabilities for customer");
	}
}