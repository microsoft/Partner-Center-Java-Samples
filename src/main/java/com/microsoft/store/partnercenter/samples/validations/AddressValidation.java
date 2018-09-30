// -----------------------------------------------------------------------
// <copyright file="AddressValidation.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.validations;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.Address;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * A scenario that showcases address validation functionality.
 */
public class AddressValidation
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #AddressValidation} class.
     * 
     * @param context The scenario context.
     */
    public AddressValidation( IScenarioContext context )
    {
        super( "Validate address", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        Address address = new Address();

        // Get the address from the console
        address.setAddressLine1(this.getContext().getConsoleHelper().readNonEmptyString("Enter the address line 1", "Address line 1 can't be empty"));
        address.setAddressLine2(this.getContext().getConsoleHelper().readOptionalString("Enter the address line 2 (optional)"));
        address.setCity(this.getContext().getConsoleHelper().readNonEmptyString("Enter the city", "City can't be empty"));
        address.setState(this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit state code", "State code can't be empty"));
        address.setCountry(this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code", "Country code can't be empty"));
        address.setPostalCode(this.getContext().getConsoleHelper().readNonEmptyString("Enter the postal/zip code", "Postal/zip code can't be empty"));

        this.getContext().getConsoleHelper().startProgress("Validating address");

        try
        {
            // Validate the address
            Boolean validationResult = partnerOperations.getValidations().isAddressValid(address);
            
            this.getContext().getConsoleHelper().stopProgress();
            
            System.out.println(validationResult ? "The address is valid." : "Invalid address");
        }
        catch (Exception exception)
        {
            this.getContext().getConsoleHelper().stopProgress();
            System.out.println("Address is invalid");
            
            if (! StringHelper.isNullOrWhiteSpace(exception.getMessage()))
            {
                this.getContext().getConsoleHelper().writeObject(exception.getMessage(), "");
            }
        }
    }
}