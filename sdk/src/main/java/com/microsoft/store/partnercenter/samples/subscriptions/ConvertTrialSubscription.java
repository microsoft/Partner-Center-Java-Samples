// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.subscriptions.Conversion;
import com.microsoft.store.partnercenter.models.subscriptions.ConversionResult;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.subscriptions.ISubscription;

/**
 * A scenario that converts a trial subscription to paid subscription.
 */
public class ConvertTrialSubscription
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #ConvertTrialSubscription} class.
     * 
     * @param context The scenario context.
     */
    public ConvertTrialSubscription( IScenarioContext context )
    {
        super( "Convert customer trial subscription", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId();
        String subscriptionId = this.obtainSubscriptionId(customerId, "Enter the ID of the trial subscription to find conversions for");
        ISubscription subscriptionOperations = partnerOperations.getCustomers().byId(customerId).getSubscriptions().byId(subscriptionId);
        
        this.getContext().getConsoleHelper().startProgress("Retrieving subscription conversions");
 
        ResourceCollection<Conversion> conversions = subscriptionOperations.getConversions().get();
       
        this.getContext().getConsoleHelper().stopProgress();

        if (conversions.getTotalCount() <= 0)
        {
            this.getContext().getConsoleHelper().error("This subscription has no conversions");
        }
        else
        {
            // Default to the first conversion.
            Conversion selectedConversion = conversions.getItems().iterator().next();
            
            this.getContext().getConsoleHelper().writeObject(conversions, "Available conversions");              
            this.getContext().getConsoleHelper().startProgress("Converting trial subscription");
            
            ConversionResult convertResult = subscriptionOperations.getConversions().create(selectedConversion);
            
            this.getContext().getConsoleHelper().stopProgress();
            this.getContext().getConsoleHelper().writeObject(convertResult, "Conversion details");
        }
    }
}