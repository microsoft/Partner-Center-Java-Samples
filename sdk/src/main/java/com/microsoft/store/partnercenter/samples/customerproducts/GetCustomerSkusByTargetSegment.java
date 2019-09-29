// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the skus related to a product that apply to a customer and that target a specific segment.
 */
public class GetCustomerSkusByTargetSegment extends BasePartnerScenario 
{
    public GetCustomerSkusByTargetSegment (IScenarioContext context) 
    {
		super("Get skus for customer by segment", context);
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
        String segment =
            this.getContext().getConsoleHelper().readNonEmptyString( 
                "The segment to filter the SKUs on",
                "The segment can't be empty" );
       
        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting skus from product {0} by segment {1} for customer {2}", productId, segment, customerId));
        
        ResourceCollection<Sku> skus = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().byTargetSegment(segment).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(skus, MessageFormat.format("Skus for customer {0} by segment {1}", productId, segment));
	}
}