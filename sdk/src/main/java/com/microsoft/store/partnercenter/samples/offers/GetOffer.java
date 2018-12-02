// -----------------------------------------------------------------------
// <copyright file="GetAccountBalance.java" company="Microsoft">
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
 * A scenario that retrieves offer details supported in a country.
 */
public class GetOffer
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetOffer} class.
     * 
     * @param context The scenario context.
     */
    public GetOffer( IScenarioContext context )
    {
        super( "Get offer details", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String offerId = this.obtainOfferId("Enter the ID of offer to retrieve");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code to get its supported offers", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting offer details for {0}", countryCode));

        Offer offer = partnerOperations.getOffers().byCountry(countryCode).byId(offerId).get();
        ResourceCollection<Offer> offerAddOns = partnerOperations.getOffers().byCountry(countryCode).byId(offerId).getAddOns().get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(offer, MessageFormat.format("Offer in {0}", countryCode));
        this.getContext().getConsoleHelper().writeObject(offerAddOns, MessageFormat.format("Offer add-ons in {0}", countryCode));
    }
}