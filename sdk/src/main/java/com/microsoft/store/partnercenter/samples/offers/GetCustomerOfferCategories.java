//-----------------------------------------------------------------------
//<copyright file="GetCustomerOfferCategories.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.offers;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.offers.OfferCategory;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that retrieves all the offer categories available for a customer.
*/
public class GetCustomerOfferCategories
	extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerOfferCategories} class.
	* 
	* @param context The scenario context.
	*/
	public GetCustomerOfferCategories( IScenarioContext context )
	{
	   super( "Get customer offer categories", context );
	}
	
	/**
	* Executes the scenario.
	*/
	@Override
	protected void runScenario()
	{
       String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve offer categories for" );
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( 
		   MessageFormat.format( "Getting offers for {0}", customerIdToRetrieve ) );
	   ResourceCollection<OfferCategory> offers = partnerOperations.getCustomers().byId(customerIdToRetrieve).getOfferCategories().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( offers, "Offers for Customer " + customerIdToRetrieve );
	}
}