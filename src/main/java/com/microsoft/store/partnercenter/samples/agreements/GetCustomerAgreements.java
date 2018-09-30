// -----------------------------------------------------------------------
// <copyright file="GetCustomerAgreements.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.agreements;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.agreements.Agreement;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Showcases getting the list of agreement details.
 */
public class GetCustomerAgreements
    extends BasePartnerScenario 
{
    public GetCustomerAgreements (IScenarioContext context) 
    {
		super("Get all customer agreements", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String selectedCustomerId = this.obtainCustomerId( "Enter the ID of the customer" );

        this.getContext().getConsoleHelper().startProgress( "Retrieving the customer's agreements" );
        
        ResourceCollection<Agreement> customerAgreements = partnerOperations.getCustomers().byId(selectedCustomerId).getAgreements().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( customerAgreements, "Customer agreements:" );
	}
}