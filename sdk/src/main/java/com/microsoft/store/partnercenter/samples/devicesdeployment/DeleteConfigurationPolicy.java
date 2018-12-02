// -----------------------------------------------------------------------
// <copyright file="DeleteConfigurationPolicy.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Deletes a configuration policy.
 */
public class DeleteConfigurationPolicy extends BasePartnerScenario 
{
    public DeleteConfigurationPolicy (IScenarioContext context) 
    {
		super("Delete a configuration policy", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to delete a configuration policy");
        String policyId = this.obtainConfigurationPolicyId("Enter the ID of the configuration policy to delete");

        this.getContext().getConsoleHelper().writeObject(policyId, "Configuration policy to be deleted");
        this.getContext().getConsoleHelper().startProgress("Deleting configuration policy");
        
        partnerOperations.getCustomers().byId(customerId).getConfigurationPolicies().byId(policyId).delete();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success("Delete configuration policy request submitted successfully!");
	}
}