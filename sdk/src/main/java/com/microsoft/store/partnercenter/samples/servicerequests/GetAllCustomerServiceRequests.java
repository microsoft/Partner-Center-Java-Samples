// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.servicerequests;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.servicerequests.ServiceRequest;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * Gets customer service requests.
 */
public class GetAllCustomerServiceRequests
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAllCustomerServiceRequests} class.
     * 
     * @param context The scenario context.
     */
    public GetAllCustomerServiceRequests( IScenarioContext context )
    {
        super( "Get customer service requests", context );
    }

    /**
     * executes the get customer service requests scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerIdToRetrieve =
            this.getContext().getConfiguration().getScenarioSettings().get( "DefaultCustomerId" );
        if ( StringHelper.isNullOrWhiteSpace( customerIdToRetrieve ) )
        {
            // prompt the user the enter the customer ID
            customerIdToRetrieve =
                this.getContext().getConsoleHelper().readNonEmptyString( "Please enter the ID of the customer to retrieve: ",
                                                                         "The customer ID can't be empty" );
        }
        else
        {
            ConsoleHelper.getInstance().warning( MessageFormat.format( "Found customer ID: {0} in configuration.",
                                                                       customerIdToRetrieve ) );
        }
        this.getContext().getConsoleHelper().startProgress( "Retrieving Customer's Service Requests" );
        ResourceCollection<ServiceRequest> serviceRequests =
            partnerOperations.getCustomers().byId( customerIdToRetrieve ).getServiceRequests().get();
        this.getContext().getConsoleHelper().stopProgress();
        if ( !serviceRequests.getItems().iterator().hasNext() )
        {
            ConsoleHelper.getInstance().warning( "No Service requests found for the given customer." );
        }
        else
        {
            this.getContext().getConsoleHelper().writeObject( serviceRequests, "Service Request results." );
        }
    }

}
