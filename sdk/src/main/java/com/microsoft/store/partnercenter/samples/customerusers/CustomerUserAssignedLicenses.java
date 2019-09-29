// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.licenses.License;
import com.microsoft.store.partnercenter.models.licenses.ServicePlan;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets customer user assigned licenses.
 */
public class CustomerUserAssignedLicenses extends BasePartnerScenario 
{
    public CustomerUserAssignedLicenses(IScenarioContext context) 
    {
		super("Get customer user assigned licenses", context);
	}

    /***
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
		// Get the customer id of customer to run this scenario
        String selectedCustomerId = this.obtainCustomerId();
        ResourceCollection<License> customerUserAssignedLicenses = null;
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        String selectedCustomerUserId = this.obtainCustomerUserId();

        // get customer user assigned licenses information.
        customerUserAssignedLicenses = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().byId(selectedCustomerUserId).getLicenses().get();

        if (customerUserAssignedLicenses != null && customerUserAssignedLicenses.getTotalCount() > 0)
        {
            System.out.println( "Customer User Assigned Licenses Count: " + customerUserAssignedLicenses.getTotalCount() );

            for ( License userLicense : customerUserAssignedLicenses.getItems() )
            {
            	Iterable<ServicePlan> servicePlans = userLicense.getServicePlans();
                System.out.println("Customer User License ServicePlans" );
                
                for (ServicePlan servicePlan : servicePlans)
                {
                	System.out.println( "Customer User License service plan display name: " + servicePlan.getDisplayName() );
                	System.out.println( "Customer User License service plan service name: " + servicePlan.getServiceName() );
                	System.out.println( "Customer User License service plan service id: " + servicePlan.getId() );
                	System.out.println( "Customer User License service plan capability status: " + servicePlan.getCapabilityStatus() );
                	System.out.println( "Customer User License service plan target type: " + servicePlan.getTargetType() );
                	System.out.println();
                }
            }
        }
    }
}