//-----------------------------------------------------------------------
//<copyright file="CustomerSubscribedSkus.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customersubscribedskus;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.licenses.SubscribedSku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that retrieves a single customer usage summary for usage based services.
*/
public class CustomerSubscribedSkus
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerUsageSummary} class.
  * 
  * @param context The scenario context.
  */
 public CustomerSubscribedSkus( IScenarioContext context )
 {
     super( "Get Customer Subscribed Skus", context );
 }

 /**
  * Executes the scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     String customerId = this.obtainCustomerIdForUsage( "Enter the ID of the customer to retrieve usage summary" );
     this.getContext().getConsoleHelper().startProgress( "Retrieving customer usage summary" );
     ResourceCollection<SubscribedSku> customerSubscribedSkus =
         partnerOperations.getCustomers().byId( customerId ).getSubscribedSkus().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( customerSubscribedSkus, "Customer Subscribed Skus" );
 }

}
