// -----------------------------------------------------------------------
// <copyright file="GetSubscriptionSupportContact.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.subscriptions.SupportContact;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the support contact of a customer's subscription.
 */
public class GetSubscriptionSupportContact
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSubscriptionSupportContact} class.
     * 
     * @param context The scenario context.
     */
    public GetSubscriptionSupportContact( IScenarioContext context )
    {
        super( "Get subscription support contact", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer whom to retrieve their Subscription");
        String subscriptionID = this.obtainSubscriptionId(customerId, "Enter the subscription ID to retrieve");

        this.getContext().getConsoleHelper().startProgress("Retrieving subscription support contact");

        SupportContact supportContact = partnerOperations.getCustomers().byId(customerId).getSubscriptions().byId(subscriptionID).getSupportContact().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(supportContact, "Subscription support contact");
    }
}