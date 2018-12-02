// -----------------------------------------------------------------------
// <copyright file="GetOffers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.offers;

import java.text.MessageFormat;
import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.offers.Offer;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves all the offers supported in a country.
 */
public class GetOffers
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetOffers} class.
     * 
     * @param context The scenario context.
     */
    public GetOffers( IScenarioContext context )
    {
        super( "Get offers", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String countryCode =
            this.getContext().getConsoleHelper().readNonEmptyString(
                "Enter the 2 digit country code to get its supported offers",
                "The country code can't be empty" );
        
        this.getContext().getConsoleHelper().startProgress( 
            MessageFormat.format( 
                "Getting offers for {0}",
                countryCode ) );
        
        ResourceCollection<Offer> offers = partnerOperations.getOffers().byCountry( countryCode ).get();
        
        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( 
            offers,
            MessageFormat.format( "Offers in {0}", countryCode ) );
    }

}
