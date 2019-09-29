// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.devicesdeployment;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.devicesdeployment.Device;
import com.microsoft.store.partnercenter.models.devicesdeployment.DevicePolicyUpdateRequest;
import com.microsoft.store.partnercenter.models.devicesdeployment.PolicyCategory;
import com.microsoft.store.partnercenter.models.utils.KeyValuePair;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Updates devices with configuration policy.
 */
public class UpdateDevicesPolicy extends BasePartnerScenario 
{
    public UpdateDevicesPolicy(IScenarioContext context) 
    {
		super("Update configuration policy of devices", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the Customer Id to update the devices for");
        String configurationPolicyId = this.obtainConfigurationPolicyId("Enter the ID of the Configuration Policy to update the device with");
        String deviceId = this.obtainDeviceId("Enter the Device Id to update");

        List<KeyValuePair<PolicyCategory, String>> policyToBeAded = new ArrayList<KeyValuePair<PolicyCategory, String>>();

        policyToBeAded.add(
            new KeyValuePair<PolicyCategory, String>
            (
                PolicyCategory.OOBE,
                configurationPolicyId 
            )
        );

        Device deviceToUpdate = new Device(); 

        deviceToUpdate.setId(deviceId);
        deviceToUpdate.setPolicies(policyToBeAded);

        List<Device> devices = new ArrayList<Device>();

        devices.add(deviceToUpdate);

        DevicePolicyUpdateRequest devicePolicyUpdateRequest = new DevicePolicyUpdateRequest(); 

        devicePolicyUpdateRequest.setDevices(devices);
           
        this.getContext().getConsoleHelper().startProgress("Updating Devices with Configuration Policy");
        
        String trackingLocation = partnerOperations.getCustomers().byId(customerId).getDevicePolicy().update(devicePolicyUpdateRequest);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(trackingLocation, "Tracking Location to track the status");
        this.getContext().getConsoleHelper().success("Update Devices Request submitted successfully!");
	}
}