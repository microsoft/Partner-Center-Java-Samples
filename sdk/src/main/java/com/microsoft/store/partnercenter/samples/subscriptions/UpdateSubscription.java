// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.subscriptions.Subscription;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that updates an existing customer subscription.
 */
public class UpdateSubscription
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #UpdateSubscription} class.
     * 
     * @param context The scenario context.
     */
    public UpdateSubscription( IScenarioContext context )
    {
        super( "Update existing customer subscription", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId();
        String subscriptionId = this.obtainSubscriptionId( customerId, "Enter the ID of the subscription to update" );
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer subscription" );
        Subscription existingSubscription =
            partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId ).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( existingSubscription, "Existing subscription" );
        this.getContext().getConsoleHelper().startProgress( "Incrementing subscription quantity" );
        existingSubscription.setQuantity( existingSubscription.getQuantity() + 1 );
        Subscription updatedSubscription =
            partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId ).patch( existingSubscription );
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( updatedSubscription, "Updated subscription" );
    }
}