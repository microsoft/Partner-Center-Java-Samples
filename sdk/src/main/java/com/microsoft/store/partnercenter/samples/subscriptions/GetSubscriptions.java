// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.subscriptions.Subscription;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer details.
 */
public class GetSubscriptions
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSubscriptions} class.
     * 
     * @param context The scenario context.
     */
    public GetSubscriptions( IScenarioContext context )
    {
        super( "Get customer Subscriptions", context );
    }

    /**
     * Executes the get customer details scenario.
     */
    @Override
    protected void runScenario()
    {
        String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer Subscriptions" );
        ResourceCollection<Subscription> customerSubscriptions = partnerOperations.getCustomers().byId( customerIdToRetrieve ).getSubscriptions().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customerSubscriptions, "Customer Subscriptions" );
    }
}