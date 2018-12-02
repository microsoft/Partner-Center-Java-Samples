//-----------------------------------------------------------------------
//<copyright file="GetSubscriptionUsageRecords.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.ratedusage;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.usage.SubscriptionMonthlyUsageRecord;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetSubscriptionUsageRecords
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetCustomerDetails} class.
  * 
  * @param context The scenario context.
  */
 public GetSubscriptionUsageRecords( IScenarioContext context )
 {
     super( "Get customer subscriptions usage records", context );
 }

 /**
  * Executes the get customer details scenario.
  */
 @Override
 protected void runScenario()
 {
     String customerId = this.obtainCustomerIdForUsage( "Enter the ID of the customer to retrieve" );
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     this.getContext().getConsoleHelper().startProgress( "Retrieving customer subscriptions usage records" );
     ResourceCollection<SubscriptionMonthlyUsageRecord> usageRecords = 
    		 partnerOperations.getCustomers().byId( customerId ).getSubscriptions().getUsageRecords().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( usageRecords.getTotalCount(), "Number of usage records" );
     for( SubscriptionMonthlyUsageRecord usageRecord : usageRecords.getItems() )
     {
    	 this.getContext().getConsoleHelper().writeObject( usageRecord.getResourceId(), "Id: " );
    	 this.getContext().getConsoleHelper().writeObject( usageRecord.getResourceName(), "Name");
    	 this.getContext().getConsoleHelper().writeObject( usageRecord.getTotalCost(), "TotalCost" );
     }
 }

}
