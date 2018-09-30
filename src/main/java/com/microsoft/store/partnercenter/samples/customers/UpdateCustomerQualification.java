//-----------------------------------------------------------------------
//<copyright file="UpdateCustomerQualification.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.CustomerQualification;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* Updates a single customer qualification.
*/
public class UpdateCustomerQualification
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #CreateOrder} class.
	* 
	* @param context The scenario context.
	*/
	public UpdateCustomerQualification( IScenarioContext context )
	{
	   super( "Update Customer Qualification", context );
	}
	
	/**
	* Executes the scenario.
	*/
	@Override
	protected void runScenario()
	{
	   String customerIdToRetrieve = this.obtainCustomerId( "Enter the ID of the customer to update qualification to: " + CustomerQualification.EDUCATION );
	   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
	   this.getContext().getConsoleHelper().startProgress( "Retrieving customer" );
	   CustomerQualification customerQualification = 
			   partnerOperations.getCustomers().byId( customerIdToRetrieve ).
			   getQualification().update( CustomerQualification.EDUCATION );
	   this.getContext().getConsoleHelper().stopProgress();
	   this.getContext().getConsoleHelper().writeObject( customerQualification, "Customer Qualification" );        
	}
	
}
