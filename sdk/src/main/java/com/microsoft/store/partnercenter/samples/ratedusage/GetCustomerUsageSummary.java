// -----------------------------------------------------------------------
// <copyright file="GetCustomerUsageSummary.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.ratedusage;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.usage.CustomerUsageSummary;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves a single customer usage summary for usage based services.
 */
public class GetCustomerUsageSummary
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetCustomerUsageSummary} class.
     * 
     * @param context The scenario context.
     */
    public GetCustomerUsageSummary( IScenarioContext context )
    {
        super( "Get customer usage summary", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerIdForUsage( "Enter the ID of the customer to retrieve usage summary" );
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer usage summary" );
        CustomerUsageSummary customerUsageSummary =
            partnerOperations.getCustomers().byId( customerId ).getUsageSummary().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customerUsageSummary, "Customer usage summary" );
    }
}