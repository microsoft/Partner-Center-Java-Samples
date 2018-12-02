//-----------------------------------------------------------------------
//<copyright file="AddUserMemberToDirectoryRole.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerdirectoryroles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.roles.DirectoryRole;
import com.microsoft.store.partnercenter.models.roles.UserMember;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class AddUserMemberToDirectoryRole
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public AddUserMemberToDirectoryRole( IScenarioContext context )
	{
	   super( "Add customer user to a directory role", context );
	}
	
	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
	   String customerId = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
	   String customerUserId = this.obtainCustomerUserId( "Enter the ID of the customer user to retrieve" );
	   String roleId = this.obtainDirectoryRoleId( "Enter the ID of the directory role to use" );
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Getting the customer to create new user for" );
	   Customer selectedCustomer = partnerOperations.getCustomers().byId( customerId ).get();
	   
	   // read the selected customer id from the application state.
	   String selectedCustomerId = selectedCustomer.getId();
	
	   this.getContext().getConsoleHelper().startProgress( "Getting user" );
	   CustomerUser customerUser = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers().byId( customerUserId ).get();
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().success( "Success!" );
	   this.getContext().getConsoleHelper().writeObject( customerUser, "Customer user Information" );
	   
       // Read all directory roles to find the one we need to use.
		DirectoryRole selectedRole = null;
        Iterable<DirectoryRole> directoryRoles = partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().get().getItems();
        for (DirectoryRole role : directoryRoles )
        {
        	if( role.getId().equalsIgnoreCase( roleId ) )
        	{
        		selectedRole = role;
        		break;
        	}
        }
	
	   UserMember userToAdd = new UserMember();
	   userToAdd.setUserPrincipalName( customerUser.getUserPrincipalName() );
	   userToAdd.setDisplayName( customerUser.getDisplayName() );
	   userToAdd.setId( customerUser.getId() );
	
	
	   // Add this customer user to the selected directory role.
	   this.getContext().getConsoleHelper().startProgress( "Adding user to Directory Role" );
	   UserMember userMemberAdded = partnerOperations.getCustomers().byId( selectedCustomerId ).getDirectoryRoles().byId( selectedRole.getId() ).getUserMembers().create( userToAdd );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().success( "Success!" );
	   this.getContext().getConsoleHelper().writeObject( userMemberAdded, "User Information after adding to Directory role " + selectedRole.getName() );
	
	
	}

}
