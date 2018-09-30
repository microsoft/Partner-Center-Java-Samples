//-----------------------------------------------------------------------
//<copyright file="UpdateBillingProfile.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import java.util.Random;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.BillingProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class UpdateBillingProfile
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public UpdateBillingProfile( IScenarioContext context )
	{
	   super( "Update Partner Billing Profile", context );
	}

	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Getting Partner Billing Profile" );
	   BillingProfile billingProfile = partnerOperations.getProfiles().getBillingProfile().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( billingProfile, "Partner Billing Profile before update" );
	   
	   Random random = new Random();
	   billingProfile.setPurchaseOrderNumber( String.valueOf( random.nextInt( 1000 ) + 9000 ) );
	
	   this.getContext().getConsoleHelper().startProgress( "Updating Partner Billing Profile" );
	   BillingProfile updateBillingProfile = partnerOperations.getProfiles().getBillingProfile().update( billingProfile );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( updateBillingProfile, "Partner Billing Profile after update" );        
	}

}
