// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Product;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the products in a catalog view that apply to a customer and that target a specific segment.
 */
public class GetCustomerProductsByTargetSegment extends BasePartnerScenario 
{
    public GetCustomerProductsByTargetSegment (IScenarioContext context) 
    {
		super("Get products for customer by segment", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the corresponding customer");
        String targetView = this.getContext().getConsoleHelper().readNonEmptyString(
            "Enter the target view to get its supported products", 
            "The target view can't be empty");
        String segment = this.getContext().getConsoleHelper().readNonEmptyString(
            "The segment to filter the products on", 
            "The segment can't be empty");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting products in catalog view {0} by segment {1} for customer {2}", targetView, segment, customerId));
        
        ResourceCollection<Product> products  = partnerOperations.getCustomers().byId(customerId).getProducts().byTargetView(targetView).byTargetSegment(segment).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(products, "Products for customer by segment");
	}
}