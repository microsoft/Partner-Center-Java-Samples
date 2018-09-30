// -----------------------------------------------------------------------
// <copyright file="GetCustomerManagedServices.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.managedservices.ManagedService;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets a customer list of managed services.
 */
public class GetCustomerManagedServices
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetCustomerManagedServices} class.
     * 
     * @param context The scenario context.
     */
    public GetCustomerManagedServices( IScenarioContext context )
    {
        super( "Get customer managed services", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the customer's managed services" );
        ResourceCollection<ManagedService> managedServices =
            partnerOperations.getCustomers().byId( customerId ).getManagedServices().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( managedServices, "Customer managed services" );
    }

}