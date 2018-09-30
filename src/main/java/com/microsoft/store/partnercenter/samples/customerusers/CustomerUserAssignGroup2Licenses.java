// -----------------------------------------------------------------------
// <copyright file="CustomerUserAssignGroup2Licenses.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.licenses.License;
import com.microsoft.store.partnercenter.models.licenses.LicenseAssignment;
import com.microsoft.store.partnercenter.models.licenses.LicenseGroupId;
import com.microsoft.store.partnercenter.models.licenses.LicenseUpdate;
import com.microsoft.store.partnercenter.models.licenses.SubscribedSku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Assign customer user a group2 license.
 */
public class CustomerUserAssignGroup2Licenses extends BasePartnerScenario 
{
    public CustomerUserAssignGroup2Licenses (IScenarioContext context) 
    {
		super("Assign customer user a group2 license", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer");
        String userId = this.obtainCustomerUserId("Enter the ID of the customer user to assign license");

        this.getContext().getConsoleHelper().startProgress("Getting Subscribed SKUs");
        
        List<LicenseGroupId> groupIds = new ArrayList<LicenseGroupId>(); 
 
        // A list of the groupids
        // Group2 – This group contains products that cant be managed in Azure Active Directory
        groupIds.add(LicenseGroupId.GROUP2);

        // Get the customer's group1 subscribed SKUs information. 
        ResourceCollection<SubscribedSku> customerGroup2SubscribedSkus = partnerOperations.getCustomers().byId(customerId).getSubscribedSkus().get(groupIds);
        
        this.getContext().getConsoleHelper().stopProgress();

        // Prepare the license request.
        LicenseUpdate updateLicense = new LicenseUpdate(); 
        LicenseAssignment license = new LicenseAssignment(); 

        // Select the first subscribed SKU.
        SubscribedSku sku = customerGroup2SubscribedSkus.getItems().iterator().next();

        // Assigning first subscribed sku as the license
        license.setSkuId(sku.getProductSku().getId());
        license.setExcludedPlans(null);

        List<LicenseAssignment> licenseList = new ArrayList<LicenseAssignment>();

        licenseList.add(license);
        updateLicense.setLicensesToAssign(licenseList);

        this.getContext().getConsoleHelper().startProgress("Assigning License");

        partnerOperations.getCustomers().byId(customerId).getUsers().byId(userId).getLicenseUpdates().create(updateLicense);

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().startProgress("Getting Assigned License");

        // Get customer user assigned licenses information after assigning the license.
        ResourceCollection<License> customerUserAssignedLicenses = partnerOperations.getCustomers().byId(customerId).getUsers().byId(userId).getLicenses().get(groupIds);

        this.getContext().getConsoleHelper().stopProgress();

        License userLicense = null; 
        
        for (License l : customerUserAssignedLicenses.getItems()) {
            if(license.getSkuId().equals(l.getProductSku().getId()))
            {
                userLicense = l; 
            }
        }

        this.getContext().getConsoleHelper().writeObject(userLicense, "Assigned License");
	}
}