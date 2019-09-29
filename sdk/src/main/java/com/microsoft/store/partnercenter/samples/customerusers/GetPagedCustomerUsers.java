// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerusers;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

public class GetPagedCustomerUsers extends BasePartnerScenario {

    /**
     * The customer user page size.
     */
    private final int customerUserPageSize;

	public GetPagedCustomerUsers(IScenarioContext context) {
		this( context, 0 );
	}

    public GetPagedCustomerUsers( IScenarioContext context, int customerUserPageSize )
    {
        super( "Get Paged customer users", context );
        this.customerUserPageSize = customerUserPageSize;
    }

    /**
     * Executes the scenario.
     */
    @Override
	protected void runScenario() {
    	String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the customer to run the get paged users scenario for" );
        Customer selectedCustomer = partnerOperations.getCustomers().byId( customerId ).get();
        
        // read the selected customer id from the application state.
        String selectedCustomerId = selectedCustomer.getId();
        
        // query the customers, get the first page if a page size was set, otherwise get all customers
        SeekBasedResourceCollection<CustomerUser> customerUsersPage = ( this.customerUserPageSize <= 0 )
                        ? partnerOperations.getCustomers().byId( selectedCustomerId ).getUsers().get()
                        : partnerOperations.getCustomers().byId( selectedCustomerId ).getUsers().query( QueryFactory.getInstance().buildIndexedQuery( this.customerUserPageSize ) );
        this.getContext().getConsoleHelper().stopProgress();
        // create a customer enumerator which will aid us in traversing the customer pages
        IResourceCollectionEnumerator<SeekBasedResourceCollection<CustomerUser>> customerUsersEnumerator =
            partnerOperations.getEnumerators().getCustomerUsers().create( customerUsersPage );
        int pageNumber = 1;
        while ( customerUsersEnumerator.hasValue() )
        {
            // print the current customer user results page
            this.getContext().getConsoleHelper().writeObject( customerUsersEnumerator.getCurrent(),
                                                              MessageFormat.format( "Customer User Page: {0}",
                                                                                    pageNumber++ ) );
            System.out.println();
            System.out.println( "Press Enter to retrieve the next customer users page" );
            ConsoleHelper.getInstance().getScanner().nextLine();
            this.getContext().getConsoleHelper().startProgress( "Getting next customer users page" );
            // get the next page of customers
            customerUsersEnumerator.next();
            this.getContext().getConsoleHelper().stopProgress();
        }
	}

}
