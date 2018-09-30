// -----------------------------------------------------------------------
// <copyright file="CreateConfigurationPolicy.java" company="Microsoft">
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
 * Creates a new configuration policy for devices.
 */
public class CreateConfigurationPolicy extends BasePartnerScenario 
{
    public CreateConfigurationPolicy (IScenarioContext context) 
    {
		super("Create a new configuration policy", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to create the configuration policy for");

        List<PolicySettingsType> policySettings = new ArrayList<PolicySettingsType>();

        policySettings.add(PolicySettingsType.OOBE_USER_NOT_LOCAL_ADMIN);
        policySettings.add(PolicySettingsType.SKIP_EULA);

        ConfigurationPolicy configurationPolicyToCreate = new ConfigurationPolicy(); 
        
        configurationPolicyToCreate.setDescription("This configuration policy is created by the SDK samples");
        configurationPolicyToCreate.setName("Test Config Policy");
        configurationPolicyToCreate.setPolicySettings(policySettings);

        this.getContext().getConsoleHelper().writeObject(configurationPolicyToCreate, "New configuration policy Information");
        this.getContext().getConsoleHelper().startProgress("Creating Configuration Policy");
        
        ConfigurationPolicy createdConfigurationPolicy = partnerOperations.getCustomers().byId(customerId).getConfigurationPolicies().create(configurationPolicyToCreate);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success("Success!");
        this.getContext().getConsoleHelper().writeObject(createdConfigurationPolicy, "Created configuration policy Information");
	}
}