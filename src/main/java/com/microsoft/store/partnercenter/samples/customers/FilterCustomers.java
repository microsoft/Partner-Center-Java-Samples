// -----------------------------------------------------------------------
// <copyright file="FilterCustomers.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.customers.CustomerSearchField;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.query.filters.FieldFilterOperation;
import com.microsoft.store.partnercenter.models.query.filters.SimpleFieldFilter;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Returns customers according to a provided filter.
 */
public class FilterCustomers
    extends BasePartnerScenario
{
    /**
     * The search field.
     */
    private final CustomerSearchField customerSearchField;

    /**
     * Initializes a new instance of the {@link #FilterCustomers} class.
     * 
     * @param title The scenario title.
     * @param customerSearchField The search filed.
     * @param context The scenario context.
     */
    public FilterCustomers( String title, CustomerSearchField customerSearchField, IScenarioContext context )
    {
        super( title, context );
        this.customerSearchField = customerSearchField;
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String searchPrefix = this.getContext().getConsoleHelper().readNonEmptyString( "Enter the prefix to search for",
                                                                                       "The entered prefix is empty" );
        this.getContext().getConsoleHelper().startProgress( "Filtering" );
        ResourceCollection<Customer> customers =
            partnerOperations.getCustomers().query( QueryFactory.getInstance().buildSimpleQuery( new SimpleFieldFilter( this.customerSearchField.toString(),
                                                                                                          searchPrefix,
                                                                                                          FieldFilterOperation.STARTS_WITH ) ) );
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customers, "Customer matches" );
    }

}
