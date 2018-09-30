//-----------------------------------------------------------------------
//<copyright file="GetCustomerOffers.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.offers;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.offers.Offer;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that retrieves all the offers available for a customer.
*/
public class GetCustomerOffers
	extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerOffers} class.
	* 
	* @param context The scenario context.
	*/
	public GetCustomerOffers( IScenarioContext context )
	{
	   super( "Get customer offers", context );
	}
	
	/**
	* Executes the scenario.
	*/
	@Override
	protected void runScenario()
	{
       String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve offers for" );
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( MessageFormat.format( "Getting offers for {0}",
	                                                                             customerIdToRetrieve ) );
	   ResourceCollection<Offer> offers = partnerOperations.getCustomers().byId( customerIdToRetrieve ).getOffers().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( offers, "Offers for Customer " + customerIdToRetrieve );
	}

}
