// -----------------------------------------------------------------------
// <copyright file="GetSubscriptionProvisioningStatus.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.subscriptions.SubscriptionProvisioningStatus;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves a customer subscription's provisioning status.
 */
public class GetSubscriptionProvisioningStatus
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSubscriptionProvisioningStatus} class.
     * 
     * @param context The scenario context.
     */
    public GetSubscriptionProvisioningStatus( IScenarioContext context )
    {
        super( "Get subscription provisioning status", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer whom to retrieve their Subscription provisioning status");
        String subscriptionID = this.obtainSubscriptionId(customerId, "Enter the subscription ID to retrieve");
        
        this.getContext().getConsoleHelper().startProgress("Retrieving subscription provisioning status");
        
        SubscriptionProvisioningStatus provisioningStatus = partnerOperations.getCustomers().byId(customerId).getSubscriptions().byId(subscriptionID).getProvisioningStatus().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(provisioningStatus, "Subscription provisioning status");
    }
}