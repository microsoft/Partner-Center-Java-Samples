//-----------------------------------------------------------------------
//<copyright file="GetSubscription.java" company="Microsoft">
//   Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.subscriptions.Subscription;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer details.
 */
public class GetSubscription extends BasePartnerScenario 
{
    /**
     * Initializes a new instance of the {@link #GetCustomerDetails} class.
     * 
     * @param context The scenario context.
     */
    public GetSubscription(IScenarioContext context)
    {
        super("Get customer subscription", context);
    }

    /**
     * Executes the get customer details scenario.
     */
    @Override
    protected void runScenario() 
    {
        String customerId = this.obtainCustomerId("Enter the ID of the customer to retrieve");
        String subscriptionId = this.obtainSubscriptionId(customerId, "Enter the ID of the subscription to retrieve");
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress("Retrieving customer Subscription");
        Subscription subscriptionDetails = partnerOperations.getCustomers().byId(customerId).getSubscriptions()
                .byId(subscriptionId).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(subscriptionDetails, "Customer Subscription");
    }
}