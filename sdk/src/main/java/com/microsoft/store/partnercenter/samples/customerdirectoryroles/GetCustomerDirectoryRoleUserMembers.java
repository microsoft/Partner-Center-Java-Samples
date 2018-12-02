//-----------------------------------------------------------------------
//<copyright file="GetCustomerDirectoryRoleUserMembers.java" company="Microsoft">
//Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerdirectoryroles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.roles.UserMember;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetCustomerDirectoryRoleUserMembers
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDetails} class.
	* 
	* @param context The scenario context.
	*/
	public GetCustomerDirectoryRoleUserMembers( IScenarioContext context )
	{
	   super( "Get Customer Directory Role User Members", context );
	}
	
	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
		   String customerId = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
		   String roleId = this.obtainDirectoryRoleId( "Enter the ID of the directory role to use" );
		   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
		   this.getContext().getConsoleHelper().startProgress( "Getting the customer to create new user for" );

           // Get all members having the selected directory role.
           SeekBasedResourceCollection<UserMember> userMembers = partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().byId( roleId ).getUserMembers().get();
           this.getContext().getConsoleHelper().stopProgress();
		   this.getContext().getConsoleHelper().success( "Success!" );
		   this.getContext().getConsoleHelper().writeObject( userMembers, "User Members who are having the selected directory role" );

	}

}
