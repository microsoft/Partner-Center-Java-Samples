// -----------------------------------------------------------------------
// <copyright file="CreateDevices.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.devicesdeployment.Device;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Creates new devices under an existing device batch.
 */
public class CreateDevices extends BasePartnerScenario 
{
    public CreateDevices (IScenarioContext context) 
    {
		super("Create new devices", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer to create the devices for");
        String deviceBatchId = this.obtainDeviceBatchId("Enter the ID of the Device batch to add the devices to");

        List<Device> devicesToBeUploaded = new ArrayList<Device>();

        Device device1ToAdd = new Device(); 

        device1ToAdd.setHardwareHash("DummyHash1234");
        device1ToAdd.setProductKey("00329-00000-0003-AA606");
        device1ToAdd.setSerialNumber("1R9-ZNP67");

        devicesToBeUploaded.add(device1ToAdd);

        Device device2ToAdd = new Device(); 

        device2ToAdd = new Device(); 

        device2ToAdd.setHardwareHash("DummyHash12345");
        device2ToAdd.setProductKey("00329-00000-0003-AA606");
        device2ToAdd.setSerialNumber("1R9-ZNP67");

        devicesToBeUploaded.add(device2ToAdd);

        this.getContext().getConsoleHelper().writeObject(devicesToBeUploaded, "New devices");
        this.getContext().getConsoleHelper().startProgress("Creating devices");
        
        String trackingLocation = partnerOperations.getCustomers().byId(customerId).getDeviceBatches().byId(deviceBatchId).getDevices().create(devicesToBeUploaded);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(trackingLocation, "Tracking location to track the status");
        this.getContext().getConsoleHelper().success("Create devices request submitted successfully!");
	}
}