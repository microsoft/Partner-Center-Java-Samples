// -----------------------------------------------------------------------
// <copyright file="GetCustomerLicensesDeploymentAnalytics .java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.analytics;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.analytics.CustomerLicensesDeploymentInsights;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer's licenses deployment analytics.
 */
public class GetCustomerLicensesDeploymentAnalytics  extends BasePartnerScenario 
{
    public GetCustomerLicensesDeploymentAnalytics (IScenarioContext context) 
    {
		super("Get customer licenses deployment analytics", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );

        this.getContext().getConsoleHelper().startProgress( "Retrieving customer licenses deployment analytics" );
        
        ResourceCollection<CustomerLicensesDeploymentInsights> customerLicensesDeploymentAnalytics  = 
            partnerOperations.getCustomers().byId(customerIdToRetrieve).getAnalytics().getLicenses().getDeployment().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( customerLicensesDeploymentAnalytics, "Customer licenses deployment analytics" );
	}
}