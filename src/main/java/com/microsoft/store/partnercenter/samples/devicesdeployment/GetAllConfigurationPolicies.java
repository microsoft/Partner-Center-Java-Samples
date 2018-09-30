// -----------------------------------------------------------------------
// <copyright file="GetAllConfigurationPolicies.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.devicesdeployment.ConfigurationPolicy;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets all configuration policies of a customer.
 */
public class GetAllConfigurationPolicies extends BasePartnerScenario 
{
    public GetAllConfigurationPolicies (IScenarioContext context) 
    {
		super("Get all configuration policies", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to get the configuration policies");

        this.getContext().getConsoleHelper().startProgress("Querying Configuration policies");
        
        ResourceCollection<ConfigurationPolicy> configPolicies = partnerOperations.getCustomers().byId(customerId).getConfigurationPolicies().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(configPolicies, "Configuration policies");
	}
}