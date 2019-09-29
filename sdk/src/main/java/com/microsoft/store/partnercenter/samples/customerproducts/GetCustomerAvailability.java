// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.Availability;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the availability of a product's sku for a customer.
 */
public class GetCustomerAvailability extends BasePartnerScenario 
{
    public GetCustomerAvailability (IScenarioContext context) 
    {
		super("Get availability for a customer", context);
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
        String availabilityId = this.obtainAvailabilityId("Enter the ID of the availability");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting availability {0} for product {1} and sku {2} for customer {3}", availabilityId, productId, skuId, customerId));
        
        Availability skuAvailability  = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().byId(skuId).getAvailabilities().byId(availabilityId).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(skuAvailability, "Availability for customer");
	}
}