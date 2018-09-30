//-----------------------------------------------------------------------
//<copyright file="GetCustomerDirectoryRoles.java" company="Microsoft">
//Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerdirectoryroles;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.roles.DirectoryRole;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Gets a single customer details.
*/
public class GetCustomerDirectoryRoles
	extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #GetCustomerDirectoryRoles} class.
	* 
	* @param context The scenario context.
	*/
	public GetCustomerDirectoryRoles( IScenarioContext context )
	{
	   super( "Get Customer Directory Roles", context );
	}
	
	/**
	* Executes the get customer details scenario.
	*/
	@Override
	protected void runScenario()
	{
		   String customerId = this.obtainCustomerId( "Enter the ID of the customer to retrieve" );
		   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
		   
		   // Read all directory roles to get the first one's id.
		   this.getContext().getConsoleHelper().startProgress( "Getting Directory Role" );
		   ResourceCollection<DirectoryRole> directoryRoles = partnerOperations.getCustomers().byId( customerId ).getDirectoryRoles().get();
		   this.getContext().getConsoleHelper().stopProgress();		

		   this.getContext().getConsoleHelper().success( "Success!" );
		   this.getContext().getConsoleHelper().writeObject( directoryRoles, "Directory roles" );
	}

}
