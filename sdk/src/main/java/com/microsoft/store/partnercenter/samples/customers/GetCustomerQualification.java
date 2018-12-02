//-----------------------------------------------------------------------
//<copyright file="GetCustomerQualification.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.CustomerQualification;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that gets customer qualification
*/
public class GetCustomerQualification
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #CreateOrder} class.
  * 
  * @param context The scenario context.
  */
 public GetCustomerQualification( IScenarioContext context )
 {
     super( "Get Customer Qualification", context );
 }

 /**
  * Executes the scenario.
  */
 @Override
 protected void runScenario()
 {
     String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving customer" );
     CustomerQualification customerQualification = partnerOperations.getCustomers().byId( customerIdToRetrieve ).getQualification().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( customerQualification, "Customer Qualification" );        
 }

}
