// -----------------------------------------------------------------------
// <copyright file="GetServiceRequestSupportTopics.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.servicerequests;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.servicerequests.SupportTopic;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the list of support topics.
 */
public class GetServiceRequestSupportTopics
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetServiceRequestSupportTopics} class.
     * 
     * @param context The scenario context.
     */
    public GetServiceRequestSupportTopics( IScenarioContext context )
    {
        super( "Get a list of service request support topics", context );
    }

    /**
     * executes the get service request support topics scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting support topics" );
        // Get support Topics
        ResourceCollection<SupportTopic> supportTopicsCollection =
            partnerOperations.getServiceRequests().getSupportTopics().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( supportTopicsCollection, "Support topics collection" );
    }

}
