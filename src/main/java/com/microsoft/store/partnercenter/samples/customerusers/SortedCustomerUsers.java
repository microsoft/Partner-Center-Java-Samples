// -----------------------------------------------------------------------
// <copyright file="SortedCustomerUsers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.query.sort.Sort;
import com.microsoft.store.partnercenter.models.query.sort.SortDirection;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Sorts the customer users.
 */
public class SortedCustomerUsers extends BasePartnerScenario
{
	public SortedCustomerUsers(IScenarioContext context) {
		super("Get sorted list of Customer Users", context);
	}

	/***
	 * Execute the scenario
	 */
	@Override
	protected void runScenario() {
		// Get the customer id of customer to run this scenario
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the sorted list of customer users:" );

        // Read the customer users for selectedCustomerId providing the sort option.
        SeekBasedResourceCollection<CustomerUser> customerUsers = partnerOperations.getCustomers().byId( customerId ).getUsers().query(QueryFactory.getInstance().buildIndexedQuery(100, 0, null, new Sort("DisplayName", SortDirection.DESCENDING)));

        if (customerUsers != null && customerUsers.getTotalCount() > 0)
        {
        	System.out.println( "Customer user count: " + customerUsers.getTotalCount() );
        	System.out.println();

            for ( CustomerUser user : customerUsers.getItems() )
            {
                System.out.println( "Name: " + user.getDisplayName() );
                System.out.println( "Id: " + user.getId() );
                System.out.println();
            }
        }
	}

}
