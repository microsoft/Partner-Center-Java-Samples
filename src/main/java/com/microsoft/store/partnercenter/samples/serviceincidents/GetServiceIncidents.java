//-----------------------------------------------------------------------
//<copyright file="GetServiceIncidents.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.serviceincidents;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.serviceincidents.ServiceIncidents;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that creates a new order for a customer.
*/
public class GetServiceIncidents
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #CreateOrder} class.
  * 
  * @param context The scenario context.
  */
 public GetServiceIncidents( IScenarioContext context )
 {
     super( "Get Service Incidents", context );
 }

 /**
  * Executes the scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

     this.getContext().getConsoleHelper().startProgress( "Getting partner usage summary" );
     ResourceCollection<ServiceIncidents> serviceIncidents = partnerOperations.getServiceIncidents().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( serviceIncidents, "Service Incidents" );
 }

}

