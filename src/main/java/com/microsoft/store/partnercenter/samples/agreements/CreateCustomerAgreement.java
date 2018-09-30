// -----------------------------------------------------------------------
// <copyright file="CreateCustomerAgreement.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.agreements;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.Contact;
import com.microsoft.store.partnercenter.models.agreements.Agreement;
import com.microsoft.store.partnercenter.models.agreements.AgreementType;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.utils.StringHelper;

import org.joda.time.DateTime;

/**
 * Showcases creation of a customer agreement.
 */
public class CreateCustomerAgreement   
    extends BasePartnerScenario 
{
    public CreateCustomerAgreement (IScenarioContext context) 
    {
		super("Create a new customer agreement", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        String selectedCustomerId = this.obtainCustomerId("Enter the ID of the customer to create the agreement for");
        String selectedUserId = this.obtainCustomerUserId("Enter the user ID of the partner to create customer's agreement");

        String agreementTemplateId = this.getContext().getConfiguration().getScenarioSettings().get("DefaultAgreementTemplateId");

        // Currently, the only supported value is 998b88de-aa99-4388-a42c-1b3517d49490, which is the unique identifier for the Microsoft Cloud Agreement. 
        if ( StringHelper.isNullOrWhiteSpace( agreementTemplateId ) )
        {
            // The value was not set in the configuration, prompt the user the enter value
            agreementTemplateId = 
                this.getContext().getConsoleHelper().readNonEmptyString(
                    "Enter the agreement template ID", "The agreement template ID can't be empty");
        }
        else
        {
            System.out.println( 
                MessageFormat.format( 
                    "Found agreement template ID: {0} in configuration.",
                    agreementTemplateId ) );
        }

        Contact contact = new Contact();

        contact.setEmail("first.last@outlook.com");
        contact.setFirstName("First");
        contact.setLastName("Last");
        contact.setPhoneNumber("4255555555");

        Agreement agreement = new Agreement();

        agreement.setDateAgreed(new DateTime());
        agreement.setPrimaryContact(contact);
        agreement.setTemplateId(agreementTemplateId);
        agreement.setType(AgreementType.MICROSOFT_CLOUD_AGREEMENT);
        agreement.setUserId(selectedUserId);

        this.getContext().getConsoleHelper().writeObject(agreement, "New Agreement");
        this.getContext().getConsoleHelper().startProgress("Creating Agreement");

        Agreement newlyCreatedagreement = partnerOperations.getCustomers().byId(selectedCustomerId).getAgreements().create(agreement);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success("Create new agreement successfully!");
        this.getContext().getConsoleHelper().writeObject(newlyCreatedagreement, "Newly created agreement Information");
	}
}