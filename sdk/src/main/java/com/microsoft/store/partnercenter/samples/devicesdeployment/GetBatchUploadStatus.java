// -----------------------------------------------------------------------
// <copyright file="GetBatchUploadStatus.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.devicesdeployment.BatchUploadDetails;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets batch upload status.
 */
public class GetBatchUploadStatus extends BasePartnerScenario 
{
    public GetBatchUploadStatus (IScenarioContext context) 
    {
		super("Get Batch Upload Status", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the Customer Id to get the status for");
        String trackingId = this.obtainBatchUploadStatusTrackingId("Enter the batch upload status tracking Id to get the status of");

        this.getContext().getConsoleHelper().startProgress("Querying the status");

        BatchUploadDetails status = partnerOperations.getCustomers().byId(customerId).getBatchUploadStatus().byId(trackingId).get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(status, "Tracking status");
	}
}