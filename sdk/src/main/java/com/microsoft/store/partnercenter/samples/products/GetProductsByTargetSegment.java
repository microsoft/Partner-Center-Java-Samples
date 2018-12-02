// -----------------------------------------------------------------------
// <copyright file="GetProductsByTargetSegment.java" company="Microsoft">
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
 * A scenario that retrieves all the products supported in a country, in a catalog view and in a segment.
 */
public class GetProductsByTargetSegment
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetProductsByTargetSegment} class.
     * 
     * @param context The scenario context.
     */
    public GetProductsByTargetSegment( IScenarioContext context )
    {
        super( "Get products by segment", context );
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
        String segment = this.getContext().getConsoleHelper().readNonEmptyString("The segment to filter the products on", "The segment can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting products in catalog view {0}, in country {1} and in segment {2}", targetView, countryCode, segment));
       
        ResourceCollection<Product> products = partnerOperations.getProducts().byCountry(countryCode).byTargetView(targetView).byTargetSegment(segment).get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(products, MessageFormat.format("Products in catalog view {0} by segment {1}", targetView, segment));
    }
}