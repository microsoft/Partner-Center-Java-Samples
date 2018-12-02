//-----------------------------------------------------------------------
//<copyright file="GetOrderDetails.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.orders;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.orders.Order;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that retrieves a customer orders.
*/
public class GetOrderDetails
 extends BasePartnerScenario
{
 /**
  * Initializes a new instance of the {@link #GetOrders} class.
  * 
  * @param context The scenario context.
  */
 public GetOrderDetails( IScenarioContext context )
 {
     super( "Get customer order details", context );
 }

 /**
  * Executes the scenario.
  */
 @Override
 protected void runScenario()
 {
     IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
     String customerId = this.obtainCustomerId( "Enter the ID of the customer whom to retrieve their orders" );
     String orderId = this.obtainOrderId("Enter the ID of order to retrieve");
     this.getContext().getConsoleHelper().startProgress( "Retrieving customer orders" );
     Order customerOrder =
         partnerOperations.getCustomers().byId( customerId ).getOrders().byId( orderId ).get();
     this.getContext().getConsoleHelper().stopProgress();
     this.getContext().getConsoleHelper().writeObject( customerOrder, "Customer order" );
 }

}
