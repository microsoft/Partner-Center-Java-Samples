// ---------------------------------------------------------------------------
// <copyright file="CreatePartnerServiceRequest.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// ----------------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.servicerequests;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.servicerequests.ServiceRequest;
import com.microsoft.store.partnercenter.models.servicerequests.ServiceRequestSeverity;
import com.microsoft.store.partnercenter.models.servicerequests.SupportTopic;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * Creates a new service request.
 */
public class CreatePartnerServiceRequest
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #CreatePartnerServiceRequest} class.
     * 
     * @param context The scenario context.
     */
    public CreatePartnerServiceRequest( IScenarioContext context )
    {
        super( "Create a new partner service request", context );
    }

    /**
     * executes the create partner service request scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String supportTopicId =
            this.getContext().getConfiguration().getScenarioSettings().get( "DefaultSupportTopicId" );
        
        if ( StringHelper.isNullOrEmpty( supportTopicId.toString() ) )
        {
            this.getContext().getConsoleHelper().startProgress( "Fetching support topics" );

            // Get the list of support topics
            ResourceCollection<SupportTopic> supportTopicsCollection =
                partnerOperations.getServiceRequests().getSupportTopics().get();
            
            this.getContext().getConsoleHelper().stopProgress();
            this.getContext().getConsoleHelper().writeObject( supportTopicsCollection, "Support topics" );
            
            // prompt the user the enter the support topic ID
            supportTopicId =
                this.getContext().getConsoleHelper().readNonEmptyString( 
                    "Please enter the support topic ID ",
                    "The support topic ID can't be empty" );
        }
        else
        {
            ConsoleHelper.getInstance().warning( 
                MessageFormat.format( 
                    "Found support topic ID: {0} in configuration.",
                    supportTopicId ) );
        }

        ServiceRequest serviceRequestToCreate = new ServiceRequest();
        
        serviceRequestToCreate.setTitle( "TrialSR" );
        serviceRequestToCreate.setDescription( "Ignore this SR" );
        serviceRequestToCreate.setSeverity( ServiceRequestSeverity.CRITICAL );
        serviceRequestToCreate.setSupportTopicId( supportTopicId );
        
        this.getContext().getConsoleHelper().startProgress( "Creating Service Request" );
        
        ServiceRequest serviceRequest =
            partnerOperations.getServiceRequests().create( serviceRequestToCreate, "en-US" );
            
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( serviceRequest, "Created Service Request" );
    }
}