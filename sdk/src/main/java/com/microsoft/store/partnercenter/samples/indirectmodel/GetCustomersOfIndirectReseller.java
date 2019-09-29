// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.indirectmodel;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.customers.CustomerSearchField;
import com.microsoft.store.partnercenter.models.query.IQuery;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.query.filters.FieldFilterOperation;
import com.microsoft.store.partnercenter.models.query.filters.SimpleFieldFilter;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the list of customers associated to an indirect reseller.
 */
public class GetCustomersOfIndirectReseller extends BasePartnerScenario 
{
    /**
     * Initializes a new instance of the {@link #CreateCustomerForIndirectReseller} class.
     * 
     * @param context The scenario context.
     */
    public GetCustomersOfIndirectReseller (IScenarioContext context) 
    {
		super("Get customers of indirect reseller", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String indirectResellerId = this.obtainIndirectResellerId("Enter the ID of the indirect reseller: ");

        this.getContext().getConsoleHelper().startProgress("Getting customers of the indirect reseller");

        SimpleFieldFilter filter = new SimpleFieldFilter(
            CustomerSearchField.INDIRECT_RESELLER.toString(), 
            FieldFilterOperation.STARTS_WITH, 
            indirectResellerId);
            
        // Create an IQuery object to pass to the query method.
        IQuery query = QueryFactory.getInstance().buildSimpleQuery(filter);

        // Get the collection of matching customers.
        SeekBasedResourceCollection<Customer> customersPage = partnerOperations.getCustomers().query(query);

        this.getContext().getConsoleHelper().stopProgress();

        // Create a customer enumerator which will aid us in traversing the customer pages.
        IResourceCollectionEnumerator<SeekBasedResourceCollection<Customer>> customersEnumerator =
             partnerOperations.getEnumerators().getCustomers().create(customersPage);
        
        int pageNumber = 1;

        while(customersEnumerator.hasValue())
        {
            // Print the current customer results page.
            this.getContext().getConsoleHelper().writeObject(
                customersEnumerator.getCurrent(), MessageFormat.format("Customer Page: {0}", pageNumber++));

            System.out.println();
            System.out.println("Press any key to retrieve the next customers page");
            System.console().readLine();

            this.getContext().getConsoleHelper().startProgress("Getting next customers page");

            // Get the next page of customers.
            customersEnumerator.next();

            this.getContext().getConsoleHelper().stopProgress();
        }
	}
}