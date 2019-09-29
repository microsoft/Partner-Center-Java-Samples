// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.offers;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.offers.OfferCategory;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that gets all offer categories.
 */
public class GetOfferCategories
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetOfferCategories} class.
     * 
     * @param context The scenario context.
     */
    public GetOfferCategories( IScenarioContext context )
    {
        super( "Get offer categories", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String countryCode =
            this.getContext().getConsoleHelper().readNonEmptyString( "Enter the 2 digit country code to get its supported offer categories",
                                                                     "The country code can't be empty" );
        this.getContext().getConsoleHelper().startProgress( MessageFormat.format( "Getting offer categories for {0}",
                                                                                  countryCode ) );
        ResourceCollection<OfferCategory> offerCategories = partnerOperations.getOfferCategories().byCountry( countryCode ).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( offerCategories,
                                                          MessageFormat.format( "Offer categories in {0}",
                                                                                countryCode ) );
    }
}