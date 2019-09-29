// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.orders;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.orders.Order;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves a customer orders.
 */
public class GetOrders
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetOrders} class.
     * 
     * @param context The scenario context.
     */
    public GetOrders( IScenarioContext context )
    {
        super( "Get customer orders", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId( "Enter the ID of the customer whom to retrieve their orders" );
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer orders" );

        ResourceCollection<Order> customerOrders =
            partnerOperations.getCustomers().byId( customerId ).getOrders().get();
            
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customerOrders, "Customer orders" );
    }

}
