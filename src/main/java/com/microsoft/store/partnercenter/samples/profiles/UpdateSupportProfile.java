//-----------------------------------------------------------------------
//<copyright file="UpdateSupportProfile.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import java.util.Random;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.SupportProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class UpdateSupportProfile
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public UpdateSupportProfile( IScenarioContext context )
	{
	   super( "Update Partner Support Profile", context );
	}

	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Getting Partner Support Profile" );
	   SupportProfile supportProfile = partnerOperations.getProfiles().getSupportProfile().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( supportProfile, "Partner Support Profile before update" );
	   
       SupportProfile newSupportProfile = new SupportProfile();
       newSupportProfile.setEmail ( supportProfile.getEmail() );
       newSupportProfile.setWebsite ( supportProfile.getWebsite() );
       Random random = new Random();
       newSupportProfile.setTelephone ( String.valueOf( ( (long) ( random.nextDouble() * 1000000000L ) + 9000000000L ) ) );

	
	   this.getContext().getConsoleHelper().startProgress( "Updating Partner Support Profile" );
	   SupportProfile updateSupportProfile = partnerOperations.getProfiles().getSupportProfile().update( newSupportProfile );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( updateSupportProfile, "Partner Support Profile after update" );        
	}

}
