// -----------------------------------------------------------------------
// <copyright file="GetIndirectResellersOfCustomer.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.indirectmodel;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.relationships.PartnerRelationship;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the list of indirect resellers associated to the indirect CSP partner of a customer.
 */
public class GetIndirectResellersOfCustomer extends BasePartnerScenario 
{
    public GetIndirectResellersOfCustomer (IScenarioContext context) 
    {
		super("Get indirect resellers of a customer", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer: ");

        this.getContext().getConsoleHelper().startProgress("Getting indirect resellers of a customer");

        ResourceCollection<PartnerRelationship> indirectResellers =
            partnerOperations.getCustomers().byId(customerId).getRelationships().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(
                indirectResellers, 
                MessageFormat.format( "Indirect Resellers of customer: {0}", customerId));
	}
}