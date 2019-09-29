// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.subscriptions.SupportContact;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves a customer subscription.
 */
public class UpdateSubscriptionSupportContact
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #UpdateSubscriptionSupportContact} class.
     * 
     * @param context The scenario context.
     */
    public UpdateSubscriptionSupportContact( IScenarioContext context )
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

        // Here we are the updating the support contact with the same object retrieved above. You can update it with a new object that has valid VAR values.
        SupportContact updatedSupportContact = partnerOperations.getCustomers().byId(customerId).getSubscriptions().byId(subscriptionID).getSupportContact().update(supportContact);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(updatedSupportContact, "Subscription support contact");
    }
}