// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.Availability;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the availability of a product's SKU..
 */
public class GetAvailability
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAvailability} class.
     * 
     * @param context The scenario context.
     */
    public GetAvailability( IScenarioContext context )
    {
        super( "Get availability", context );
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
        String availabilityId = this.obtainAvailabilityId("Enter the ID of the availability");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code of the availability", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Getting availability {0} for product {1} and sku {2} in country {3}", availabilityId, productId, skuId, countryCode));
       
        Availability availability = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).getAvailabilities().byId(availabilityId).get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(availability, "Availability");
    }
}