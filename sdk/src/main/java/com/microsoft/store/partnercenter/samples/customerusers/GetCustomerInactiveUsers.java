// -----------------------------------------------------------------------
// <copyright file="GetCustomerInactiveUsers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.query.IQuery;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.query.SeekOperation;
import com.microsoft.store.partnercenter.models.query.filters.FieldFilterOperation;
import com.microsoft.store.partnercenter.models.query.filters.SimpleFieldFilter;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class GetCustomerInactiveUsers extends BasePartnerScenario {

	public GetCustomerInactiveUsers(IScenarioContext context) {
		super("Get list of inactive Customer Users", context);
	}

	/***
	 * Executes the scenario
	 */
	@Override
	protected void runScenario() {
		// Get the customer id of customer to run this scenario
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the list of inactive users for customer id: " + customerId );

        // Read the customer users for selectedCustomerId providing the filter option.        
        SimpleFieldFilter filter = new SimpleFieldFilter("UserStatus", "Inactive", FieldFilterOperation.EQUALS );
        IQuery simpleQueryWithFilter = QueryFactory.getInstance().buildSimpleQuery(filter);

        // Read inactive customer users in a batch
        SeekBasedResourceCollection<CustomerUser> customerUsers = partnerOperations.getCustomers().byId(customerId).getUsers().query(simpleQueryWithFilter);
        IResourceCollectionEnumerator<SeekBasedResourceCollection<CustomerUser>> customerUsersEnumerator = partnerOperations.getEnumerators().getCustomerUsers().create(customerUsers);

        while ( customerUsersEnumerator.hasValue() )
        {
            System.out.println( "Customer user page count: " + customerUsersEnumerator.getCurrent().getTotalCount() );

            for ( CustomerUser user : customerUsersEnumerator.getCurrent().getItems() )
            {
                System.out.println( "Customer User Id: " + user.getId() );
                System.out.println( "Company User Name: " + user.getDisplayName() );
                System.out.println( "Company User State: " + user.getState() );
                System.out.println();
            }

            if ( customerUsers.getLinks().getNext() == null )
            {
                break;
            }

            IQuery seekQuery = QueryFactory.getInstance().buildSeekQuery( SeekOperation.NEXT, 0, 0, null, null, customerUsers.getContinuationToken() );
            customerUsers = partnerOperations.getCustomers().byId(customerId).getUsers().query( seekQuery );
            customerUsersEnumerator = partnerOperations.getEnumerators().getCustomerUsers().create( customerUsers );
        }

	}
}