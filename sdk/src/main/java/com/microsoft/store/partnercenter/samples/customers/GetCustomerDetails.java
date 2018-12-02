// -----------------------------------------------------------------------
// <copyright file="GetCustomerDetails.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a single customer details.
 */
public class GetCustomerDetails
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetCustomerDetails} class.
     * 
     * @param context The scenario context.
     */
    public GetCustomerDetails( IScenarioContext context )
    {
        super( "Get a customer details", context );
    }

    /**
     * Executes the get customer details scenario.
     */
    @Override
    protected void runScenario()
    {
        String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer" );
        Customer customerDetails = partnerOperations.getCustomers().byId( customerIdToRetrieve ).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customerDetails, "Customer details" );        
    }

}
