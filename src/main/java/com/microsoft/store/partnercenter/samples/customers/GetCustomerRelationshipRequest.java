// -----------------------------------------------------------------------
// <copyright file="GetCustomerRelationshipRequest.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.relationshiprequests.CustomerRelationshipRequest;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the request which establishes a relationship between the partner and their customers.
 */
public class GetCustomerRelationshipRequest
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetCustomerRelationshipRequest} class.
     * 
     * @param context The scenario context.
     */
    public GetCustomerRelationshipRequest( IScenarioContext context )
    {
        super( "Get customer relationship request", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Retrieving customer relationship request" );
        CustomerRelationshipRequest customerRelationshipRequest =
            partnerOperations.getCustomers().getRelationshipRequests().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( customerRelationshipRequest,
                                                          "Customer relationship request" );
    }

}
