// -----------------------------------------------------------------------
// <copyright file="GetSkus.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the SKUs related to a product that are supported in a country.
 */
public class GetSkus
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSkus} class.
     * 
     * @param context The scenario context.
     */
    public GetSkus( IScenarioContext context )
    {
        super( "Get SKUs", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String productId = this.obtainProductId("Enter the ID of the corresponding product");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code to get its supported skus", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting skus for product {0} in country {1}", productId, countryCode));
       
        ResourceCollection<Sku> skus = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(skus, MessageFormat.format("SKUs for product {0}", productId));
    }
}