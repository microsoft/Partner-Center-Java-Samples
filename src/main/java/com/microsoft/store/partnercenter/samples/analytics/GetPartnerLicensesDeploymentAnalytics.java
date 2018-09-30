// -----------------------------------------------------------------------
// <copyright file="GetPartnerLicensesDeploymentAnalytics.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.analytics;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.analytics.PartnerLicensesDeploymentInsights;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets partner's licenses deployment analytics.
 */
public class GetPartnerLicensesDeploymentAnalytics extends BasePartnerScenario 
{
    public GetPartnerLicensesDeploymentAnalytics (IScenarioContext context) 
    {
		super("Get partner licenses deployment analytics", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Retrieving partner licenses deployment analytics" );
        
        ResourceCollection<PartnerLicensesDeploymentInsights> partnerLicensesDeploymentAnalytics  = 
            partnerOperations.getAnalytics().getLicenses().getDeployment().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( partnerLicensesDeploymentAnalytics, "Partner licenses deployment analytics" );
	}
}