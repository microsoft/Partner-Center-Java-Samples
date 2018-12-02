//-----------------------------------------------------------------------
//<copyright file="RemoveCustomerUserMemberFromDirectoryRole.java" company="Microsoft">
//Copyright (c) Microsoft Corporation. All rights reserved.
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
public class RemoveCustomerUserMemberFromDirectoryRole
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public RemoveCustomerUserMemberFromDirectoryRole( IScenarioContext context )
	{
	   super( "Remove Customer User Member From Directory Role", context );
	}
	
	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
		String customerId = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
		String userId = this.obtainCustomerUserId( "Enter the ID of the customer-user to retrieve" );
		String roleId = this.obtainDirectoryRoleId( "Enter the ID of the directory role to use" );
		IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

		Customer selectedCustomer = partnerOperations.getCustomers().byId( customerId ).get();
		   
		CustomerUser customerUser = partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( userId ).get();

        // Read all directory roles to find the one we need to use.
		DirectoryRole selectedRole = null;
        Iterable<DirectoryRole> directoryRoles = partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().get().getItems();
        for (DirectoryRole role : directoryRoles )
        {
        	if( role.getId().equalsIgnoreCase(roleId) )
        	{
        		selectedRole = role;
        		break;
        	}
        }

        UserMember userToUpdate = new UserMember();
        userToUpdate.setUserPrincipalName ( customerUser.getUserPrincipalName() );
        userToUpdate.setDisplayName ( customerUser.getDisplayName() );
        userToUpdate.setId ( customerUser.getId() );

        // Add this customer user to the selected directory role.
        //UserMember userMemberAdded = partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().byId( selectedRole.getId() ).getUserMembers().create( userToUpdate );

        try
        {
            // Remove customer user from selected directory role.
        	partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().byId( selectedRole.getId() ).getUserMembers().byId( userToUpdate.getId() ).delete();

            System.out.println( "The user with user principal name: " + userToUpdate.getUserPrincipalName() + " was removed from directory role: " + selectedRole.getName() );
        }
        catch(Exception ex)
        {
        	System.out.println( "Excepion removing user: " + ex );
        }

        // Cleaning up, delete user.
        // partnerOperations.getCustomers().byId( selectedCustomer.getId() ).getUsers().byId( customerUser.getId() ).delete();
	}

}
