//-----------------------------------------------------------------------
//<copyright file="GetOrganizationProfile.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.OrganizationProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetOrganizationProfile
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerDetails} class.
  * 
  * @param context The scenario context.
  */
 public GetOrganizationProfile( IScenarioContext context )
 {
     super( "Get partner organization profile", context );
 }

 /**
  * Executes the get customer details scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving Organization Profile" );
     OrganizationProfile organizationProfile = partnerOperations.getProfiles().getOrganizationProfile().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( organizationProfile, "Organization Profile" );        
 }

}
