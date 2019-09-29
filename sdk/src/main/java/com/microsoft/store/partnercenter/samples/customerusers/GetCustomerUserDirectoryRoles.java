// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.


package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.roles.DirectoryRole;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class GetCustomerUserDirectoryRoles extends BasePartnerScenario {

    /***
     * Public constructor
     */
	public GetCustomerUserDirectoryRoles(IScenarioContext context) {
		super("Get directory roles assigned to a Customer User", context);
	}

	/***
	 * Executes the scenario.
	 */
	@Override
	protected void runScenario() {
		// Get the customer id of customer to run this scenario
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the customer to run the update user scenario for" );
        Customer selectedCustomer = partnerOperations.getCustomers().byId( customerId ).get();
        
        // read the selected customer id from the application state.
        String selectedCustomerId = selectedCustomer.getId();

        // get user directory roles.
        SeekBasedResourceCollection<CustomerUser> allCustomerUsers = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().get();
        CustomerUser selectedCustomerUser = null;
        if( allCustomerUsers.getTotalCount() >= 1 )
        {
        	selectedCustomerUser = allCustomerUsers.getItems().iterator().next();//..get .getbyId(selectedCustomerUser.Id).DirectoryRoles.Get();
        }
        ResourceCollection<DirectoryRole> userMemberships = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().byId(selectedCustomerUser.getId()).getDirectoryRoles().get();
        

        if (userMemberships != null && userMemberships.getTotalCount() > 0)
        {
            System.out.println( "Customer user directory role count: " + userMemberships.getTotalCount() );
            System.out.println( "User Principal Name: " + selectedCustomerUser.getUserPrincipalName() );
            System.out.println( "Display Name: " + selectedCustomerUser.getDisplayName() );
            System.out.println();

            for ( DirectoryRole membership : userMemberships.getItems() )
            {
            	System.out.println( "Role Id:  " + membership.getId() );
            	System.out.println();
            }
        }
	}

}
