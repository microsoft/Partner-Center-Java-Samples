// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves details of a product's sku for a customer.
 */
public class GetCustomerSku extends BasePartnerScenario 
{
    public GetCustomerSku (IScenarioContext context) 
    {
		super("Get sku for customer", context);
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
        String skuId = this.obtainSkuId("Enter the ID of the sku");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting sku details for sku {0} from product {1} for customer {2}", skuId, productId, customerId));
        
        Sku sku  = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().byId(skuId).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(sku, MessageFormat.format("Sku details for customer", skuId, productId));
	}
}