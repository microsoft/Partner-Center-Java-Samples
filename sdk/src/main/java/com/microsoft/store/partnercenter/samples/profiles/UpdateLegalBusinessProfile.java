//-----------------------------------------------------------------------
//<copyright file="UpdateLegalBusinessProfile.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.Address;
import com.microsoft.store.partnercenter.models.partners.LegalBusinessProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class UpdateLegalBusinessProfile
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public UpdateLegalBusinessProfile( IScenarioContext context )
	{
	   super( "Update Partner LegalBusiness Profile", context );
	}
	
	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Getting Partner LegalBusiness Profile" );
	   LegalBusinessProfile legalBusinessProfile = partnerOperations.getProfiles().getLegalBusinessProfile().get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( legalBusinessProfile, "Partner LegalBusiness Profile before update" );
	   
	   Address address = legalBusinessProfile.getAddress();
	   address.setAddressLine1( address.getAddressLine1() + "A" );
	   legalBusinessProfile.setAddress( address );
	
	   this.getContext().getConsoleHelper().startProgress( "Updating Partner LegalBusiness Profile" );
	   LegalBusinessProfile updateLegalBusinessProfile = partnerOperations.getProfiles().getLegalBusinessProfile().update( legalBusinessProfile );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( updateLegalBusinessProfile, "Partner LegalBusiness Profile after update" );        
	}

}
