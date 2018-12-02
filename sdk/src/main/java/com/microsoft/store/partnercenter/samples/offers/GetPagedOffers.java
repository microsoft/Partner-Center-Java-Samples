// -----------------------------------------------------------------------
// <copyright file="GetPagedOffers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.offers;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.offers.Offer;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * A scenario that retrieves all the offers supported in a country.
 */
public class GetPagedOffers
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetOffers} class.
     * 
     * @param context The scenario context.
     */
    public GetPagedOffers( IScenarioContext context )
    {
        super( "Get paged offers", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        int offset = 0;
        int pageSize = 20;

        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String countryCode =
            this.getContext().getConsoleHelper().readNonEmptyString( "Enter the 2 digit country code to get its supported offers",
                                                                     "The country code can't be empty" );
        this.getContext().getConsoleHelper().startProgress( MessageFormat.format( "Getting offers for {0}",
                                                                                  countryCode ) );
        ResourceCollection<Offer> offersSegment = partnerOperations.getOffers().byCountry( countryCode ).get( offset, pageSize );
        IResourceCollectionEnumerator<ResourceCollection<Offer>> offersEnumerator = partnerOperations.getEnumerators().getOffers().create( offersSegment );
        int pageNumber = 1;
        while ( offersEnumerator.hasValue() )
        {
            // print the current customer results page
            this.getContext().getConsoleHelper().writeObject( offersEnumerator.getCurrent(),
                                                              MessageFormat.format( "Offer Page: {0}",
                                                                                    pageNumber++ ) );
            System.out.println();
            System.out.println( "Press Enter to retrieve the next offers page" );
            ConsoleHelper.getInstance().getScanner().nextLine();
            this.getContext().getConsoleHelper().startProgress( "Getting next offers page" );
            // get the next page of customers
            offersEnumerator.next();
            this.getContext().getConsoleHelper().stopProgress();
        }
    }
}