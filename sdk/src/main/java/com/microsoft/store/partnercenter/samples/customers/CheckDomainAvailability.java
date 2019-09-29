// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that checks if a domain is still available for a customer or not.
 */
public class CheckDomainAvailability extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #CheckDomainAvailability} class.
     * 
     * @param context The scenario context.
     */
    public CheckDomainAvailability(IScenarioContext context) 
    {
        super("Check domain availability", context);
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String domainPrefix = this.getContext().getConsoleHelper()
                .readNonEmptyString("Enter a domain prefix to check its availability", "The entered domain is empty");
        this.getContext().getConsoleHelper().startProgress("Checking");
        
        boolean isDomainAvailable = partnerOperations.getDomains().byDomain(domainPrefix + ".onmicrosoft.com").exists();
        
        this.getContext().getConsoleHelper().stopProgress();
        
        if (!isDomainAvailable) {
            this.getContext().getConsoleHelper().success("This domain prefix is available!");
        } else {
            this.getContext().getConsoleHelper().warning("This domain prefix is unavailable.");
        }
    }
}