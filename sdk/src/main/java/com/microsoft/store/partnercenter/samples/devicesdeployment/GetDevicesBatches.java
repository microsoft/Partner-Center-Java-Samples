// -----------------------------------------------------------------------
// <copyright file="GetDevicesBatches.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.devicesdeployment.DeviceBatch;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets devices batches.
 */
public class GetDevicesBatches extends BasePartnerScenario 
{
    public GetDevicesBatches (IScenarioContext context) 
    {
		super("Get devices batches", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the Customer Id to get the devices batches for");

        this.getContext().getConsoleHelper().startProgress("Querying for the devices batches");

        ResourceCollection<DeviceBatch> devicesBatches = partnerOperations.getCustomers().byId(customerId).getDeviceBatches().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(devicesBatches, "Device batches");
	}
}