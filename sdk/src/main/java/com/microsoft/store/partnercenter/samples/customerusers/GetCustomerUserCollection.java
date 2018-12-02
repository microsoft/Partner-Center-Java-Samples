// -----------------------------------------------------------------------
// <copyright file="GetCustomerUserCollection.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class GetCustomerUserCollection extends BasePartnerScenario {

	public GetCustomerUserCollection(IScenarioContext context) {
		super("Get a customer users collection", context);
	}

    /**
     * Executes the get customer users scenario.
     */
	@Override
	protected void runScenario() {
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting customer users collection" );
        ResourceCollection<CustomerUser> users =
            partnerOperations.getCustomers().byId( customerId ).getUsers().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( users, "Customer Users Collection" );
	}
}