// -----------------------------------------------------------------------
// <copyright file="GetCustomerServiceCostsSummary.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerservicecosts;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.servicecosts.ServiceCostsSummary;
import com.microsoft.store.partnercenter.models.servicecosts.ServiceCostsBillingPeriod;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets Customer Service Costs Summary.
 */
public class GetCustomerServiceCostsSummary extends BasePartnerScenario 
{
    public GetCustomerServiceCostsSummary (IScenarioContext context) 
    {
		super("Get customer service costs summary", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to get service costs summary");

        this.getContext().getConsoleHelper().startProgress("Getting customer service costs summary");
        
        ServiceCostsSummary customerServiceCostsSummary = partnerOperations.getCustomers().byId(customerId).getServiceCosts().byBillingPeriod(ServiceCostsBillingPeriod.MOST_RECENT).getSummary().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(customerServiceCostsSummary, "Customer Service Costs Summary");
	}
}