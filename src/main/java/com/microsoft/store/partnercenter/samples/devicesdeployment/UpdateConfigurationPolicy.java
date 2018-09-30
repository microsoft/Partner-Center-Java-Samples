// -----------------------------------------------------------------------
// <copyright file="UpdateConfigurationPolicy.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.devicesdeployment.ConfigurationPolicy;
import com.microsoft.store.partnercenter.models.devicesdeployment.PolicySettingsType;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Updates configuration policy.
 */
public class UpdateConfigurationPolicy extends BasePartnerScenario 
{
    public UpdateConfigurationPolicy (IScenarioContext context) 
    {
		super("Update configuration policy", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the Customer Id to update the configuration policy for");
        String configurationPolicyId = this.obtainConfigurationPolicyId("Enter the ID of the Configuration Policy to update");

        List<PolicySettingsType> policySettings = new ArrayList<PolicySettingsType>();

        policySettings.add(PolicySettingsType.OOBE_USER_NOT_LOCAL_ADMIN);
        policySettings.add(PolicySettingsType.REMOVE_OEM_PREINSTALLS);

        ConfigurationPolicy configPolicyToBeUpdated = new ConfigurationPolicy(); 
        
        configPolicyToBeUpdated.setId(configurationPolicyId);
        configPolicyToBeUpdated.setName("Test Config Policy");
        configPolicyToBeUpdated.setPolicySettings(policySettings);

        this.getContext().getConsoleHelper().startProgress("Updating configuration policy");
        
        ConfigurationPolicy updatedConfigurationPolicy = partnerOperations.getCustomers().byId(customerId).getConfigurationPolicies().byId(configurationPolicyId).patch(configPolicyToBeUpdated);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(updatedConfigurationPolicy, "Updated configuration policy");
	}
}