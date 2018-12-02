//-----------------------------------------------------------------------
//<copyright file="GetMpnProfile.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.profiles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.MpnProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetMpnProfile
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerDetails} class.
  * 
  * @param context The scenario context.
  */
 public GetMpnProfile( IScenarioContext context )
 {
     super( "Get partner MPN profile", context );
 }

 /**
  * Executes the get customer details scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving Mpn Profile" );
     MpnProfile mpnProfile = partnerOperations.getProfiles().getMpnProfile().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( mpnProfile, "Mpn Profile" );        
 }

}
