// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.analytics;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.analytics.CustomerLicensesUsageInsights;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer's licenses usage analytics.
 */
public class GetCustomerLicensesUsageAnalytics   extends BasePartnerScenario 
{
    public GetCustomerLicensesUsageAnalytics  (IScenarioContext context) 
    {
		super("Get customer licenses usage analytics", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );

        this.getContext().getConsoleHelper().startProgress( "Retrieving customer licenses usage analytics" );
        
        ResourceCollection<CustomerLicensesUsageInsights> customerLicensesDeploymentAnalytics = 
            partnerOperations.getCustomers().byId(customerIdToRetrieve).getAnalytics().getLicenses().getUsage().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( customerLicensesDeploymentAnalytics, "Customer licenses usage analytics" );
	}
}