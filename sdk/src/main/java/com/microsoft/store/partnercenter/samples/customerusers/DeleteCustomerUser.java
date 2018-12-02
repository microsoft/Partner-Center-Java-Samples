// -----------------------------------------------------------------------
// <copyright file="DeleteCustomerUser.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import java.util.UUID;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.models.users.PasswordProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class DeleteCustomerUser extends BasePartnerScenario {

	public DeleteCustomerUser(IScenarioContext context) {
		super("Deletes a Customer User", context);
	}

    /**
     * executes the delete customer user scenario by creating a customer and then deleting it.
     */
	@Override
	protected void runScenario() {
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the customer to run the delete user scenario for" );
        Customer selectedCustomer = partnerOperations.getCustomers().byId( customerId ).get();
        
        // read the selected customer id from the application state.
        String selectedCustomerId = selectedCustomer.getId();
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

        this.getContext().getConsoleHelper().writeObject( userToCreate, "New user Information" );
        this.getContext().getConsoleHelper().startProgress( "Creating user" );
        CustomerUser customerUserToDelete = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().create( userToCreate );
        this.getContext().getConsoleHelper().writeObject( customerUserToDelete, "Created user Information" );

        // Now delete the newly created customer user
        this.getContext().getConsoleHelper().startProgress( "Created user. Deleting the user" );
        partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().byId( customerUserToDelete.getId() ).delete();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success( "Success!" );
        this.getContext().getConsoleHelper().writeObject( customerUserToDelete, "Deleted user Information" );
	}	
}
