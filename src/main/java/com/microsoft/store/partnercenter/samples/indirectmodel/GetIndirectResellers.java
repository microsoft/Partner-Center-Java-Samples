// -----------------------------------------------------------------------
// <copyright file="GetIndirectResellers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.indirectmodel;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.relationships.PartnerRelationship;
import com.microsoft.store.partnercenter.models.relationships.PartnerRelationshipType;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the list of indirect resellers associated to the indirect CSP partner.
 */
public class GetIndirectResellers extends BasePartnerScenario 
{
    public GetIndirectResellers (IScenarioContext context) 
    {
		super("Get indirect resellers", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        this.getContext().getConsoleHelper().startProgress("Getting indirect resellers");

        ResourceCollection<PartnerRelationship> indirectResellers = partnerOperations.getRelationships().get(
            PartnerRelationshipType.IS_INDIRECT_CLOUD_SOLUTION_PROVIDER_OF);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(indirectResellers, "Indirect Resellers");
	}
}