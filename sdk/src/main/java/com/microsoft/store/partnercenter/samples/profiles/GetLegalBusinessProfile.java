//-----------------------------------------------------------------------
//<copyright file="GetLegalBusinessProfile.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.LegalBusinessProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetLegalBusinessProfile
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerDetails} class.
  * 
  * @param context The scenario context.
  */
 public GetLegalBusinessProfile( IScenarioContext context )
 {
     super( "Get partner legal business profile", context );
 }

 /**
  * Executes the get customer details scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving Legal Business Profile" );
     LegalBusinessProfile legalBusinessProfile = partnerOperations.getProfiles().getLegalBusinessProfile().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( legalBusinessProfile, "Legal Business Profile" );        
 }

}
