// -----------------------------------------------------------------------
// <copyright file="GetEntitlements.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.entitlements;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.entitlements.Entitlement;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Get customer entitlements.
 */
public class GetEntitlements extends BasePartnerScenario 
{
    public GetEntitlements (IScenarioContext context) 
    {
		super("Get entitlements", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to retrieve entitlements for");

        this.getContext().getConsoleHelper().startProgress("Retrieving customer entitlements");
        
        ResourceCollection<Entitlement> entitlements = partnerOperations.getCustomers().byId(customerId).getEntitlements().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(entitlements, "Artifact Details");
	}
}