// -----------------------------------------------------------------------
// <copyright file="GetAvailabilities.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Availability;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the availabilities of a product's SKU.
 */
public class GetAvailabilities
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAvailabilities} class.
     * 
     * @param context The scenario context.
     */
    public GetAvailabilities( IScenarioContext context )
    {
        super( "Get availabilities", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        String productId = this.obtainProductId("Enter the ID of the corresponding product");
        String skuId = this.obtainSkuId("Enter the ID of the corresponding sku");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code of the sku", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting availabilities for product {0} and sku {1} in country {2}", productId, skuId, countryCode));
       
        ResourceCollection<Availability> skuAvailabilities = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).getAvailabilities().get();
        
        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(skuAvailabilities, MessageFormat.format("Availabilities for product {0}", productId));
    }
}