// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerusers;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.licenses.License;
import com.microsoft.store.partnercenter.models.licenses.LicenseGroupId;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Get customer user assigned group1 and group2 licenses
 */
public class CustomerUserAssignedGroup1AndGroup2Licenses extends BasePartnerScenario 
{
    public CustomerUserAssignedGroup1AndGroup2Licenses (IScenarioContext context) 
    {
		super("Get customer user assigned group1 and group2 licenses", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer");
        String userId = this.obtainCustomerUserId("Enter the ID of the customer user to get assigned licenses");

        this.getContext().getConsoleHelper().startProgress("Getting customer user assigned licenses");
        
        List<LicenseGroupId> groupIds = new ArrayList<LicenseGroupId>();

        // Get the customer user assigned group1 and group2 licenses information
        //  Group1 – This group has all products whose license can be managed in the Azure Active Directory (AAD).
        //  Group2 – This group contains products that cant be managed in Azure Active Directory
        groupIds.add(LicenseGroupId.GROUP1);
        groupIds.add(LicenseGroupId.GROUP2);

        ResourceCollection<License> customerUserAssignedGroup1Group2Licenses = partnerOperations.getCustomers().byId(customerId).getUsers().byId(userId).getLicenses().get(groupIds);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(customerUserAssignedGroup1Group2Licenses, "Customer User Assigned Group1 and Group2 Licenses");
	}
}