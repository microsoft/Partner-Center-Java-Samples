// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.devicesdeployment.Device;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets devices of a device batch.
 */
public class GetDevices extends BasePartnerScenario 
{
    public GetDevices (IScenarioContext context) 
    {
		super("Get devces", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the Customer Id to get the devices for");
        String deviceBatchId = this.obtainDeviceBatchId("Enter the ID of the Device batch to retrieve the devices of");

        this.getContext().getConsoleHelper().startProgress("Querying for the devices");

        ResourceCollection<Device> devices = partnerOperations.getCustomers().byId(customerId).getDeviceBatches().byId(deviceBatchId).getDevices().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(devices, "Devices");
	}
}