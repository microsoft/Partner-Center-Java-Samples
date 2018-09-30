// -----------------------------------------------------------------------
// <copyright file="GetSku.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves details of a product's SKU.
 */
public class GetSku
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSku} class.
     * 
     * @param context The scenario context.
     */
    public GetSku( IScenarioContext context )
    {
        super( "Get SKU", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String productId = this.obtainProductId("Enter the ID of the corresponding product");
        String skuId = this.obtainSkuId("Enter the ID of the sku");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code of the sku", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting sku details for sku {0} and product {1} in country {2}", skuId, productId, countryCode));
       
        Sku sku = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(sku, MessageFormat.format("SKU details of {0}", skuId));
    }
}