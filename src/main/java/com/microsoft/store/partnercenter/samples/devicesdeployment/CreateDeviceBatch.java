// -----------------------------------------------------------------------
// <copyright file="CreateDeviceBatch.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.devicesdeployment.Device;
import com.microsoft.store.partnercenter.models.devicesdeployment.DeviceBatchCreationRequest;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Creates a new device batch with devices.
 */
public class CreateDeviceBatch extends BasePartnerScenario 
{
    public CreateDeviceBatch (IScenarioContext context) 
    {
		super("Create a new device batch", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to create the device batch for");

        Device deviceToAdd = new Device(); 

        deviceToAdd.setHardwareHash("DummyHash1234");
        deviceToAdd.setProductKey("00329-00000-0003-AA606");
        deviceToAdd.setSerialNumber("1R9-ZNP67");

        List<Device> devicesToBeUploaded = new ArrayList<Device>();

        devicesToBeUploaded.add(deviceToAdd);

        DeviceBatchCreationRequest newDeviceBatch = new DeviceBatchCreationRequest();

        newDeviceBatch.setBatchId("SDKTestDeviceBatch");
        newDeviceBatch.setDevices(devicesToBeUploaded);
        
        this.getContext().getConsoleHelper().writeObject(newDeviceBatch, "New device batch");
        this.getContext().getConsoleHelper().startProgress("Creating device batch");
        
        String trackingLocation = partnerOperations.getCustomers().byId(customerId).getDeviceBatches().create(newDeviceBatch);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(trackingLocation, "Tracking location to track the status");
        this.getContext().getConsoleHelper().success("Create device batch request submitted successfully!");
	}
}