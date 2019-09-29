// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.CustomerBillingProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that updates a customer's billing profile.
 */
public class UpdateCustomerBillingProfile
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #UpdateCustomerBillingProfile} class.
     * 
     * @param context The scenario context.
     */
    public UpdateCustomerBillingProfile( IScenarioContext context )
    {
        super( "Update customer billing profile", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        String customerId = this.obtainCustomerId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting customer billing profile" );
        CustomerBillingProfile billingProfile =
            partnerOperations.getCustomers().byId( customerId ).getProfiles().getBilling().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( billingProfile, "Customer billing profile" );
        this.getContext().getConsoleHelper().startProgress( "Updating the customer billing profile" );
        // append some A's to some of the customer's billing profile properties
        billingProfile.setFirstName( billingProfile.getFirstName() + "A" );
        billingProfile.setLastName( billingProfile.getLastName() + "A" );
        billingProfile.setCompanyName( billingProfile.getCompanyName() + "A" );
        // update the billing profile
        CustomerBillingProfile updatedBillingProfile =
            partnerOperations.getCustomers().byId( customerId ).getProfiles().getBilling().update( billingProfile );
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( updatedBillingProfile, "Updated customer billing profile" );
    }

}
