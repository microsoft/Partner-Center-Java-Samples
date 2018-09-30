// -----------------------------------------------------------------------
// <copyright file="GetCustomerSkuDownloadOptions.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerproducts;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.SkuDownloadOptions;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the download options of a product's sku for a customer.
 */
public class GetCustomerSkuDownloadOptions extends BasePartnerScenario 
{
    public GetCustomerSkuDownloadOptions (IScenarioContext context) 
    {
		super("Get sku download options for customer", context);
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
            MessageFormat.format("Getting the SKU download options for sku {0} from product {1} for customer {2}", skuId, productId, customerId));

        ResourceCollection<SkuDownloadOptions> sku  = partnerOperations.getCustomers().byId(customerId).getProducts().byId(productId).getSkus().byId(skuId).getDownloadOptions().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(sku, "Sku download options for customer");
	}
}