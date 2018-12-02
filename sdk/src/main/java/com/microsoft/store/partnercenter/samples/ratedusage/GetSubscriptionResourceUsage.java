//-----------------------------------------------------------------------
//<copyright file="GetSubscriptionResourceUsage.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.ratedusage;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.usage.AzureResourceMonthlyUsageRecord;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetSubscriptionResourceUsage
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetSubscriptionResourceUsage} class.
  * 
  * @param context The scenario context.
  */
 public GetSubscriptionResourceUsage( IScenarioContext context )
 {
     super( "Get subscription resource usage", context );
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
     ResourceCollection<AzureResourceMonthlyUsageRecord> usageRecords = 
    		 partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId ).getUsageRecords().getResources().get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( usageRecords, "Subscription resource usage records" );
     System.out.println( "Demonstrate iterating over the resources." );
     this.getContext().getConsoleHelper().writeObject( usageRecords.getTotalCount(), "Quantity" );
     for( AzureResourceMonthlyUsageRecord usageRecord : usageRecords.getItems() )
     {
    	 System.out.println();
    	 System.out.println( "Id: " + usageRecord.getResourceId() );
    	 System.out.println( "Name: " + usageRecord.getResourceName() );
    	 System.out.println( "Category: " + usageRecord.getCategory());
    	 System.out.println( "QuantityUsed: " + usageRecord.getQuantityUsed() );
    	 System.out.println( "Unit: " + usageRecord.getUnit());
    	 System.out.println( "TotalCost" + usageRecord.getTotalCost() );
     }
 }

}
