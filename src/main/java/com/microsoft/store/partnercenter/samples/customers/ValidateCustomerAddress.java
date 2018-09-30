//-----------------------------------------------------------------------
//<copyright file="ValidateCustomerAddress.java" company="Microsoft">
//Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.countryvalidationrules.CountryValidationRules;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that showcases country validation rules.
*/
public class ValidateCustomerAddress
extends BasePartnerScenario
{
	/**
	* Initializes a new instance of the {@link #ValidateCustomerAddress} class.
	* 
	* @param context The scenario context.
	*/
	public ValidateCustomerAddress( IScenarioContext context )
	{
	   super( "Validate customer address", context );
	}
	
	/**
	* Executes the scenario.
	*/
	@Override
	protected void runScenario()
	{
		IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
		String countryCode = this.getContext().getConsoleHelper().readNonEmptyString(
			"Enter the 2 digit country code to get its validation rules", 
			"The country code can't be empty");

		this.getContext().getConsoleHelper().startProgress( "Retrieving country validation rules" );
	
		CountryValidationRules countryValidationRules = 
			   partnerOperations.getCountryValidationRules().byCountry( countryCode ).get();
	
		this.getContext().getConsoleHelper().stopProgress();
		this.getContext().getConsoleHelper().writeObject( countryValidationRules, "Country validation rules" );        
	}
}