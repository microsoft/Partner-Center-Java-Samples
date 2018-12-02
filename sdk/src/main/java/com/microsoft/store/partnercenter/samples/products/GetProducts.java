// -----------------------------------------------------------------------
// <copyright file="GetProducts.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.Product;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the products supported in a country and in a catalog view.
 */
public class GetProducts
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetProduct} class.
     * 
     * @param context The scenario context.
     */
    public GetProducts( IScenarioContext context )
    {
        super( "Get products", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code to get its supported products", "The country code can't be empty");
        String targetView = this.getContext().getConsoleHelper().readNonEmptyString("Enter the target view to get its supported products", "The target view can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting products in catalog view {0} in country {1}", targetView, countryCode));
       
        ResourceCollection<Product> products = partnerOperations.getProducts().byCountry(countryCode).byTargetView(targetView).get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(products, MessageFormat.format("Products in catalog view {0}", targetView));
    }
}