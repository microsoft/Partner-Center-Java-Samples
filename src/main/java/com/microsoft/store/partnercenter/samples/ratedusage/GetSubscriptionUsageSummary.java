//-----------------------------------------------------------------------
//<copyright file="GetSubscriptionUsageSummary.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.ratedusage;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.usage.SubscriptionUsageSummary;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetSubscriptionUsageSummary
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerDetails} class.
  * 
  * @param context The scenario context.
  */
 public GetSubscriptionUsageSummary( IScenarioContext context )
 {
     super( "Get customer Subscription Usage Summary", context );
 }

 /**
  * Executes the get customer details scenario.
  */
 @Override
 protected void runScenario()
 {
     String customerId = this.obtainCustomerIdForUsage( "Enter the ID of the customer to retrieve" );
     String subscriptionId = this.obtainSubscriptionIdForUsage( customerId, "Enter the ID of the subscription with usage" );
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving customer" );
     SubscriptionUsageSummary subscriptionsUsageSummary = 
    		 partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId ).getUsageSummary().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( subscriptionsUsageSummary, "Subscription usage summary details" );        
 }

}
