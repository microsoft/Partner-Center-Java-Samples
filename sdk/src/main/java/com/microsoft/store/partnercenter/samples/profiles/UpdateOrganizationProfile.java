//-----------------------------------------------------------------------
//<copyright file="UpdateOrganizationProfile.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import java.util.Random;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.Address;
import com.microsoft.store.partnercenter.models.partners.OrganizationProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class UpdateOrganizationProfile
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public UpdateOrganizationProfile( IScenarioContext context )
	{
	   super( "Update Partner Organization Profile", context );
	}

	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Getting Partner Organization Profile" );
	   OrganizationProfile organizationProfile = partnerOperations.getProfiles().getOrganizationProfile().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( organizationProfile, "Partner Organization Profile before update" );
	   
	   Address defaultAddress = organizationProfile.getDefaultAddress();
	   Random random = new Random();
	   defaultAddress.setPhoneNumber( String.valueOf( ( (long) ( random.nextDouble() * 1000000000L ) + 9000000000L ) ) );
	   organizationProfile.setDefaultAddress( defaultAddress );
	
	   this.getContext().getConsoleHelper().startProgress( "Updating Partner Organization Profile" );
	   OrganizationProfile updateOrganizationProfile = partnerOperations.getProfiles().getOrganizationProfile().update( organizationProfile );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( updateOrganizationProfile, "Partner Organization Profile after update" );        
	}

}
