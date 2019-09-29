// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that updates registers a subscription.
 */
public class RegisterSubscription
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #RegisterSubscription} class.
     * 
     * @param context The scenario context.
     */
    public RegisterSubscription( IScenarioContext context )
    {
        super( "Registers a subscription so it will be eligible for purchasing Azure Reserved Instances", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId();
        String subscriptionId = this.obtainSubscriptionId(customerId, "Enter the ID of the subscription to register");

        this.getContext().getConsoleHelper().startProgress( "Registering the subscription" );

        String response = partnerOperations.getCustomers().byId(customerId).getSubscriptions().byId(subscriptionId).getRegistration().register();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( response, "Registered subscription" );
    }
}