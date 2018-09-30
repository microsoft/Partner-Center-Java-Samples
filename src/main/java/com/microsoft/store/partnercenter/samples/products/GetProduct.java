// -----------------------------------------------------------------------
// <copyright file="GetProduct.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.Product;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves product details supported in a country.
 */
public class GetProduct
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetProduct} class.
     * 
     * @param context The scenario context.
     */
    public GetProduct( IScenarioContext context )
    {
        super( "Get product", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        String productId = this.obtainProductId("Enter the ID of the corresponding product");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code of the product", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting details for product {0} in country {1}", productId, countryCode));
       
        Product product = partnerOperations.getProducts().byCountry(countryCode).byId(productId).get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(product, MessageFormat.format("Product details of {0}", productId));
    }
}