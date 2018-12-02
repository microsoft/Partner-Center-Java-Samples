// -----------------------------------------------------------------------
// <copyright file="GetPartnerLicensesUsageAnalytics.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.analytics;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.analytics.PartnerLicensesUsageInsights;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets partner's licenses usage analytics.
 */
public class GetPartnerLicensesUsageAnalytics extends BasePartnerScenario 
{
    public GetPartnerLicensesUsageAnalytics (IScenarioContext context) 
    {
		super("Get partner licenses usage analytics", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Retrieving partner licenses usage analytics" );
        
        ResourceCollection<PartnerLicensesUsageInsights> partnerLicensesUsageAnalytics  = 
            partnerOperations.getAnalytics().getLicenses().getUsage().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( partnerLicensesUsageAnalytics, "Partner licenses usage analytics" );
	}
}