//-----------------------------------------------------------------------
//<copyright file="GetSubscriptionsByOrder.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.subscriptions.Subscription;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer details.
 */
public class GetSubscriptionsByOrder extends BasePartnerScenario 
{
    /**
     * Initializes a new instance of the {@link #GetCustomerDetails} class.
     * 
     * @param context The scenario context.
     */
    public GetSubscriptionsByOrder(IScenarioContext context) 
    {
        super("Get customer subscriptions by order", context);
    }

    /**
     * Executes the get customer details scenario.
     */
    @Override
    protected void runScenario() 
    {
        String customerId = this.obtainCustomerId("Enter the ID of the customer to retrieve");
        String orderId = this.obtainOrderId("Enter the ID of order to retrieve");
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress("Retrieving customer subscriptions by order");
        ResourceCollection<Subscription> subscriptionsbyOrder = partnerOperations.getCustomers().byId(customerId)
                .getSubscriptions().byOrder(orderId).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(subscriptionsbyOrder, "Customer Subscriptions By Order");
    }
}