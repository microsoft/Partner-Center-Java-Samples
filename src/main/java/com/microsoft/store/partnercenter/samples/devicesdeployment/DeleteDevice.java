// -----------------------------------------------------------------------
// <copyright file="DeleteDevice.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Deletes a device.
 */
public class DeleteDevice extends BasePartnerScenario 
{
    public DeleteDevice(IScenarioContext context) 
    {
		super("Delete a device", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to delete a device");
        String deviceBatchId = this.obtainDeviceBatchId("Enter the ID of the Device batch to retrieve the devices of");
        String deviceId = this.obtainDeviceId("Enter the ID of the device to delete");
     
        this.getContext().getConsoleHelper().writeObject(deviceId, "Device to be deleted");
        this.getContext().getConsoleHelper().startProgress("Deleting device");
        
        partnerOperations.getCustomers().byId(customerId).getDeviceBatches().byId(deviceBatchId).getDevices().byId(deviceId).delete();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success("Deleted the device successfully!");
	}
}