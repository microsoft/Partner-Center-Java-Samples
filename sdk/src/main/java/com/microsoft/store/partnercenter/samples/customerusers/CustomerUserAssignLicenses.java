// -----------------------------------------------------------------------
// <copyright file="CustomerUserAssignLicenses.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.licenses.License;
import com.microsoft.store.partnercenter.models.licenses.LicenseAssignment;
import com.microsoft.store.partnercenter.models.licenses.LicenseUpdate;
import com.microsoft.store.partnercenter.models.licenses.ServicePlan;
import com.microsoft.store.partnercenter.models.licenses.SubscribedSku;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.models.users.PasswordProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class CustomerUserAssignLicenses extends BasePartnerScenario {

	public CustomerUserAssignLicenses(IScenarioContext context) {
		super("Assign licenses to a Customer User", context);
	}

	/***
	 * Executes the scenario
	 */
	@Override
	protected void runScenario() {
		Customer selectedCustomer = new Customer();
		String custIdToUse = obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting customers" );
        // get the customers
        @SuppressWarnings("unused")
		SeekBasedResourceCollection<Customer> customerList = partnerOperations.getCustomers().get();
        boolean isCustomerWithSubscribedSkusExist = false;


        ResourceCollection<SubscribedSku> customerSubscribedSkus = null;
		//for ( Customer customer : customerList.getItems() )
        {
			//if ( customer.getId() != "6ffcc08f-4073-44c3-83f0-07c1d60f7274" )
			//	continue;
            // get customer's subscribed skus information.
            customerSubscribedSkus = partnerOperations.getCustomers().byId( custIdToUse ).getSubscribedSkus().get();
            if (customerSubscribedSkus != null && customerSubscribedSkus.getTotalCount() > 0)
            {
                selectedCustomer = partnerOperations.getCustomers().byId( custIdToUse ).get();
                isCustomerWithSubscribedSkusExist = true;
                //break;
            }
        }

        if (!isCustomerWithSubscribedSkusExist)
        {
            System.out.println("No customer with SubscribedSkus was found.");
        }
        else
        {
            // Create a user for this customer.
            PasswordProfile passwordProfile = new PasswordProfile();
            passwordProfile.setForceChangePassword(true);
            passwordProfile.setPassword("Password!1");

            CustomerUser userToCreate = new CustomerUser();
            userToCreate.setPasswordProfile(passwordProfile);
            userToCreate.setDisplayName( "TestDisplayName" );
            userToCreate.setFirstName( "TestFirstName" );
            userToCreate.setLastName( "TestLastName" );
            userToCreate.setUsageLocation( "US" );
            userToCreate.setUserPrincipalName(UUID.randomUUID().toString().toUpperCase() + "@" + selectedCustomer.getCompanyProfile().getDomain());

            CustomerUser createdUser = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().create( userToCreate );

            // Prepare license request.
            LicenseUpdate updateLicense = new LicenseUpdate();
            String userId = createdUser.getId();

            // Select the first subscribed sku.
            SubscribedSku sku = customerSubscribedSkus.getItems().iterator().next();
            LicenseAssignment license = new LicenseAssignment();
            license.setSkuId ( sku.getProductSku().getId() );
            license.setExcludedPlans( null );

            List<LicenseAssignment> licenseList = new ArrayList<LicenseAssignment>();
            licenseList.add(license);
            updateLicense.setLicensesToAssign ( licenseList );

            // Assign licenses to the user.
            LicenseUpdate assignLicense = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( userId ).getLicenseUpdates().create( updateLicense );
            
            if(assignLicense == null)
            {
            	System.out.println("Null value was returned");
            }

            // get customer user assigned licenses information after assigning the license.
            ResourceCollection<License>  customerUserAssignedLicenses = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( userId ).getLicenses().get();

            if (customerUserAssignedLicenses != null && customerUserAssignedLicenses.getTotalCount() > 0)
            {
                System.out.println("License was successfully assigned to the user.");
                License userLicense = customerUserAssignedLicenses.getItems().iterator().next();

                Iterable<ServicePlan> servicePlans = userLicense.getServicePlans();
                System.out.println("Customer User License ServicePlans ");
                for (ServicePlan servicePlan : servicePlans)
                {
                	System.out.println( "Customer User License service plan display name: " + servicePlan.getDisplayName() );
                	System.out.println( "Customer User License service plan service name: " + servicePlan.getServiceName() );
                	System.out.println( "Customer User License service plan service id: " + servicePlan.getId() );
                	System.out.println( "Customer User License service plan capability status: " + servicePlan.getCapabilityStatus() );
                	System.out.println();
                }
            }

            // Remove the assigned license.
            updateLicense.setLicensesToAssign ( null );
            List<String> licensesToRemove = new ArrayList<String>();
            licensesToRemove.add( sku.getProductSku().getId() );
            updateLicense.setLicensesToRemove ( licensesToRemove );
            assignLicense = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( userId ).getLicenseUpdates().create( updateLicense );

            // get customer user assigned licenses information after removing the license.
            customerUserAssignedLicenses = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( userId ).getLicenses().get();
            if (customerUserAssignedLicenses != null && customerUserAssignedLicenses.getTotalCount() > 0)
            {
            	System.out.println("Remove license operation failed.");
            }
            else
            {
            	System.out.println("License was successfully removed.");
            }

            // Try to delete the created user.
            partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( createdUser.getId() ).delete();
        }
	}

}
